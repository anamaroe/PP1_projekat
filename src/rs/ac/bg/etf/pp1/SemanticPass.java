package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;
import rs.etf.pp1.symboltable.*;

import java.util.Stack;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;

	int nVars;
	private Struct currentVarType = null; 
	boolean isVarArray = false;
	
	Obj currentMethod = null;	
	boolean returnFound = false;
	int formalMethParams = 0;
	boolean isVoid = false;
		
	/* stek svih trenutnih parametara */
	Stack<List<Struct>> allActParamsStack = new Stack<List<Struct>>();

	boolean mainFound = false;

	boolean inNamespace = false;
	String namespaceName = null;
	
	/* za break i continue */
	boolean inForLoop = false;			
	
	/* za [a, a, *p] = designator: smestanje ovih bez * */
	ArrayList<Obj> designs = new ArrayList<Obj>();
	
	Logger log = Logger.getLogger(getClass());
	
	/* Enum koji vraca tip (int, char..) na osnovu struct.getKind(), koji vraca broj: */
	public String getKindType(Struct s) {
		switch (s.getKind()) {
			case Struct.None: return "none";
			case Struct.Int: return "int";
			case Struct.Char: return "char";
			case Struct.Array: return "array";
			case Struct.Bool: return "bool";
			default: return "";
		}
	}
	
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public SemanticPass() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", new Struct(Struct.Bool)));
	}
	
	enum RelationalOp { EQUAL, NEQUAL, LESS, GREATER, LESSEQ, GREATEQ }
	RelationalOp currRelOp = null;
	
	private StringBuilder output = new StringBuilder();
	
	public String printObjNode(Obj obj) {
		output.setLength(0);
		output.append(" [");
		switch (obj.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Meth: output.append("Meth "); break;
		case Obj.Prog: output.append("Prog "); break;
		default: return "";
		}
		output.append(obj.getName());
		output.append(": ");
		Struct s = obj.getType(); 
		if(s.getKind() == 3) { // None, Int, Char, Array, Class, Bool, Enum, Interface
			output.append("Arr of ");
			output.append(getKindType(s.getElemType()));
		} else {
			output.append(getKindType(s));
		}
		output.append(", ");
		output.append(obj.getAdr());
		output.append(", ");
		output.append(obj.getLevel() + " ");
		output.append("]");
		return output.toString();
	}
	
	public void tsdump() {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		SymbolTableVisitor stv = new DumpSymbolTableVisitor();
		for (Scope s = Tab.currentScope(); s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		log.info(stv.getOutput());
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	
												  /* Visits */
	
	
	
	/*============================================== Program ============================================== */
	public void visit(ProgName progName) {
		 progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		 Tab.openScope();
		 Tab.insert(Obj.Var, "banana", Tab.intType);
		 Tab.insert(Obj.Var, "mandarina", Tab.intType);
	}
	
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	
	/* ============================================== Type ============================================== */
	public void visit(TypeNoScope typeNoScope) { // visit pri deklaraciji!
		Obj typeNode = Tab.find(typeNoScope.getTypeNameWithoutScope());
		if(typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + typeNoScope.getTypeNameWithoutScope() + " u tabeli simbola.", typeNoScope);
			typeNoScope.struct = Tab.noType;
		} else {
			if(Obj.Type == typeNode.getKind()) {
				typeNoScope.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + typeNoScope.getTypeNameWithoutScope() + "nije tip.", typeNoScope);
				typeNoScope.struct = Tab.noType;
			}
		}
		currentVarType = typeNoScope.struct;
	}
	
	public void visit(TypeWScope typeWScope) {
		Obj typeNode = Tab.find(typeWScope.getTypeNameScope()+"::"+typeWScope.getTypeNameWithScope());
		if(typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + typeWScope.getTypeNameScope() + "::" + typeWScope.getTypeNameWithScope() + " u tabeli simbola.", typeWScope);
			typeWScope.struct = Tab.noType;
		} else {
			if(Obj.Type == typeNode.getKind()) {
				typeWScope.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + typeWScope.getTypeNameScope() + "::" + typeWScope.getTypeNameWithScope() + "nije tip.", typeWScope);
				typeWScope.struct = Tab.noType;
			}
		}
		currentVarType = typeWScope.struct;
	}
	
	/* ============================================== Array ============================================== */
	public void visit(IsArray isArray) {
		isVarArray= true;
	}
	
	public void visit(NotArray notArray) {
		isVarArray= false;
	}
	
	/* ============================================== Namespace ============================================== */
	public void visit(NamespaceName namespName) {
		inNamespace = true;
		namespaceName = namespName.getName();
	}
	
	public void visit(HasNamespace hasNamespace) {
		inNamespace = false;
		namespaceName = null;
	}
	
	/* ============================================== SingleVarDeclaration ============================================== */
	public void visit(VarSingleSemi varSingleSemi) {
		Obj node = Tab.find(varSingleSemi.getVarName());
    	if(node != Tab.noObj) {
    		if(Tab.currentScope.findSymbol(varSingleSemi.getVarName()) != null) {
    			// problem je samo ako su u istom scope-u
				report_error("Ime " + varSingleSemi.getVarName() + " je vec deklarisano!", varSingleSemi);    		
				isVarArray = false;
				currentVarType = null;
				return;
    		}
    	}
		nVars++;
	//	report_info("Deklarisana promenljiva "+ varSingleSemi.getVarName(), varSingleSemi);
		if(isVarArray == true) {
			Struct s = new Struct(Struct.Array, currentVarType);
			if(inNamespace && currentMethod == null) {  
				Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + varSingleSemi.getVarName(), s);
			} else {
				Obj varNode = Tab.insert(Obj.Var, varSingleSemi.getVarName(), s);
			}
			isVarArray = false;
		}
		if(inNamespace && currentMethod == null) {
			Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + varSingleSemi.getVarName(), currentVarType);
		} else {
			Obj varNode = Tab.insert(Obj.Var, varSingleSemi.getVarName(), currentVarType);
		}
		currentVarType = null;
	}	 

	/* ============================================== MultipleVarsDeclaration ============================================== */
	public void visit(MoreVarDecl moreVarDecl) {
		Obj node = Tab.find(moreVarDecl.getVarName());
    	if(node != Tab.noObj) {
    		if(Tab.currentScope.findSymbol(moreVarDecl.getVarName()) != null) {
    			// problem je samo ako su u istom scope-u
				report_error("Ime " + moreVarDecl.getVarName() + " je vec deklarisano!", moreVarDecl);    
				isVarArray = false;
				return;
    		}
    	}
		nVars++;
//		report_info("Deklarisana promenljiva "+ moreVarDecl.getVarName(), moreVarDecl);
		if(isVarArray == true) {
			Struct s = new Struct(Struct.Array, currentVarType);
			if(inNamespace && currentMethod == null) {
				Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + moreVarDecl.getVarName(), s);
			} else {
				Obj varNode = Tab.insert(Obj.Var, moreVarDecl.getVarName(), s);
			}
			isVarArray = false;
		}
		if(inNamespace && currentMethod == null) {
			Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + moreVarDecl.getVarName(), currentVarType);
		} else {
			// not in namespace
			Obj varNode = Tab.insert(Obj.Var, moreVarDecl.getVarName(), currentVarType);
		}
		currentVarType = null;
	}
	
	public void visit(VarCommaSeparated varCommaSeparated) {
		Obj node = Tab.find(varCommaSeparated.getVarName());
    	if(node != Tab.noObj) {
    		if(Tab.currentScope.findSymbol(varCommaSeparated.getVarName()) != null) {
    			// problem je samo ako su u istom scope-u
				report_error("Ime " + varCommaSeparated.getVarName() + " je vec deklarisano!", varCommaSeparated);    	
				isVarArray = false;
				return;
    		}
    	}
    	nVars++;
	//	report_info("Deklarisana promenljiva "+ varCommaSeparated.getVarName(), varCommaSeparated);
		if(isVarArray == true) {
			Struct s = new Struct(Struct.Array, currentVarType);
			if(inNamespace && currentMethod == null) {
				Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + varCommaSeparated.getVarName(), s);
			} else {
				Obj varNode = Tab.insert(Obj.Var, varCommaSeparated.getVarName(), s);
			}
			isVarArray = false;
		}
		if(inNamespace && currentMethod == null) {
			Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + varCommaSeparated.getVarName(), currentVarType);
		} else {
			// not in namespace
			Obj varNode = Tab.insert(Obj.Var, varCommaSeparated.getVarName(), currentVarType);
		} 
	}
	
	public void visit(LastVarDecl lastVarDecl) {
		Obj node = Tab.find(lastVarDecl.getVarName());
    	if(node != Tab.noObj) {
    		if(Tab.currentScope.findSymbol(lastVarDecl.getVarName()) != null) {
    			// problem je samo ako su u istom scope-u
				report_error("Ime " + lastVarDecl.getVarName() + " je vec deklarisano!", lastVarDecl);    	
				isVarArray = false;
				return;
    		}
    	}
		nVars++;
//		report_info("Deklarisana promenljiva "+ lastVarDecl.getVarName(), lastVarDecl);
		if(isVarArray == true) {
			Struct s = new Struct(Struct.Array, currentVarType);
			if(inNamespace && currentMethod == null) {
				Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + lastVarDecl.getVarName(), s);
			} else {
				Obj varNode = Tab.insert(Obj.Var, lastVarDecl.getVarName(), s);
			}
			isVarArray = false;
		}
		if(inNamespace && currentMethod == null) {
			Obj varNode = Tab.insert(Obj.Var, namespaceName + "::" + lastVarDecl.getVarName(), currentVarType);
		} else {
			Obj varNode = Tab.insert(Obj.Var, lastVarDecl.getVarName(), currentVarType);
		} 
	}
	
	/* ============================================== ConstDeclarations ============================================== */	
	public void visit(ConstDecl constDecl) {
		currentVarType = constDecl.getType().struct;
	}
	
	public void visit(NumConst numConst) {
		if(!Tab.intType.equals(currentVarType)) {
			report_error("Tip konstante " + currentVarType.getKind() + " i dodeljena vrednost " + numConst.getNum() + " nisu istog tipa ", numConst); 	    		
		} else {
			String name = numConst.getName();
			Obj constNode = Tab.find(name);
			if(constNode != Tab.noObj) {
	    		if(Tab.currentScope.findSymbol(numConst.getName()) != null) {
	    			// problem je samo ako su u istom scope-u
					report_error("Ime " + numConst.getName() + " je vec deklarisano!", numConst);    	
					isVarArray = false;
					return;
	    		}
	    	} else {
	    		if(inNamespace) {
	    		name = namespaceName + "::" + name;
	    	}
	    	if(isVarArray == true) {
	    		Struct s = new Struct(Struct.Array, currentVarType);
	    		constNode = Tab.insert(Obj.Con, name, s);
	    	} else {
	    		constNode = Tab.insert(Obj.Con, name, currentVarType);
	    	}
	    	constNode.setAdr(numConst.getNum());
//			report_info("Deklarisana je konstanta " + name + " = " + numConst.getNum(), numConst);
	    	}
		}
		isVarArray = false;
	}
	
	public void visit(CharConst charConst) {
		if(!Tab.charType.equals(currentVarType)) {
			report_error("Tip konstante " + currentVarType.getKind() + " i dodeljena vrednost " + charConst.getChrVal() + " nisu istog tipa ", charConst); 	
		} else {
			String name = charConst.getName();
			Obj constNode = Tab.find(name);
			if(constNode != Tab.noObj) {
	    		if(Tab.currentScope.findSymbol(charConst.getName()) != null) {
	    			// problem je samo ako su u istom scope-u
					report_error("Ime " + charConst.getName() + " je vec deklarisano!", charConst);    	
					isVarArray = false;
					return;
	    		}
	    	} else {
	    		if(inNamespace) {
	    		name = namespaceName + "::" + name;
	    	}
    		if(isVarArray == true) {
	    		Struct s = new Struct(Struct.Array, currentVarType);
	    		constNode = Tab.insert(Obj.Con, name, s);
	    	} else {
	    		constNode = Tab.insert(Obj.Con, name, currentVarType);
	    	}
	    	constNode.setAdr(charConst.getChrVal());
//			report_info("Deklarisana je konstanta " + name + " = " + charConst.getChrVal(), charConst);
	    	}
		}
		isVarArray = false;
	}
	
	public void visit(BoolConst boolConst) {
		if(currentVarType.getKind() != 5) {
			report_error("Tip konstante " + currentVarType.getKind() + " i dodeljena vrednost " + boolConst.getBoolVal() + " nisu istog tipa ", boolConst); 	    		
		} else {
			String name = boolConst.getName();
			Obj constNode = Tab.find(name);
			if(constNode != Tab.noObj) {
	    		if(Tab.currentScope.findSymbol(boolConst.getName()) != null) {
	    			// problem je samo ako su u istom scope-u
					report_error("Ime " + boolConst.getName() + " je vec deklarisano!", boolConst);    	
					isVarArray = false;
					return;
	    		}
	    	} else {
	    		if(inNamespace) {
	    		name = namespaceName + "::" + name;
	    	}
    		if(isVarArray == true) {
	    		Struct s = new Struct(Struct.Array, currentVarType);
	    		constNode = Tab.insert(Obj.Con, name, s);
	    	} else {
	    		constNode = Tab.insert(Obj.Con, name, currentVarType);
	    	}
	    	if(boolConst.getBoolVal() == true) {
	    		constNode.setAdr(1);
	    	} else {
	    		constNode.setAdr(0);
	    	}
	//		report_info("Deklarisana je konstanta " + name + " = " + boolConst.getBoolVal(), boolConst);
	    	}
		}
		isVarArray = false;
	}
	
	/* ============================================== MethodDeclaration ============================================== */	
	public void visit(ReturnStatement returnStatement) {
		if(!isVoid) {
			report_error("Funkcija nije deklarisana kao void ali nema povratnu vrednost", returnStatement);
		}
		returnFound = true;
	}
	
	public void visit(ReturnStatementExpr returnStatementExpr) {
		if(isVoid) {
			//report_error("Funkcija je void a vraca izraz", returnStatementExpr);
			// greska se prijavljuje u runtime-u
			return;
		}
		returnFound = true;
		if(returnStatementExpr.getExpression().struct.getKind() == 5 && currentMethod.getType().getKind() == 5) { // bool ret val
			return;
		}
		if(currentMethod.getType()!=returnStatementExpr.getExpression().struct){
			report_error("Tip izraza u return naredbi nije kompatibilan sa tipom povratne vrednosti funkcije " + getKindType(currentMethod.getType()), returnStatementExpr);
			return;
    	}
	}
	
	public void visit(VoidRetType voidRetType) {
		isVoid = true;
		voidRetType.struct = Tab.noType;    
		currentVarType = new Struct(Struct.None);
	}
	
	public void visit(NonVoidRetType nonVoidRetType) {
		nonVoidRetType.struct = nonVoidRetType.getType().struct;
	}
	
	public void visit(MethodTypeName methodTypeName) {
		String name;
		if(inNamespace) {
			name = namespaceName + "::" + methodTypeName.getMethodName();
		} else {
			name = methodTypeName.getMethodName();
		}
		Obj constNode = Tab.find(name);
		if(constNode != Tab.noObj) {
			report_error("Ime " + name + " je vec deklarisano!", methodTypeName);   
			currentMethod = Tab.noObj;
    		methodTypeName.obj = Tab.noObj;
    		Tab.openScope();
			return;
    	} 
		Struct s = currentVarType;
		if(isVoid) {
			s = new Struct(Struct.None);
		}
		methodTypeName.obj = Tab.insert(Obj.Meth, name, s);
		currentMethod = methodTypeName.obj;
		if("main".equals(currentMethod.getName()) && (currentMethod.getLevel() == 0)){
			mainFound = true;
		}
	//	report_info("Deklarisana funkcija " + getKindType(currentVarType) + " " + methodTypeName.getMethodName(), methodTypeName);
		Tab.openScope();
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		if(!returnFound && !isVoid) {
			report_error("Semanticka greska na liniji "+methodDeclaration.getLine()+": funkcija "+currentMethod.getName()+" nema return.", methodDeclaration);
		}
		//currentMethod.setLevel(formalMethParams);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
		//formalMethParams = 0;
		isVoid = false;
	}
	
	public void visit(SetLevel setLevel) {
		currentMethod.setLevel(formalMethParams);
		formalMethParams = 0;
	}
	
	public void visit(SingleFormalParam singleFormalParam) { // Type:t IDENT:name Array
		formalMethParams++;
		// trazi u celoj tabeli simbola
		Obj formPar = Tab.find(singleFormalParam.getName());
		if(formPar != Tab.noObj) {
			// vec postoji nesto sa ovim nazivom, da li je u lokalnom scope-u?
			if(Tab.currentScope.findSymbol(singleFormalParam.getName()) != null) {
				report_error("Vec postoji formalni parametar sa imenom "+singleFormalParam.getName(), singleFormalParam);
				return;
			}
		}
		// nema ga u tabeli simbola, ubaci ga
		Struct parType = singleFormalParam.getType().struct; // currentVarType
		if(isVarArray) {
			parType = new Struct(Struct.Array, parType);
		}
		Tab.insert(Obj.Var, singleFormalParam.getName(), parType);
		isVarArray = false;
	}
	
	/* ============================================== Factors ============================================== */	
	public void visit(FactorNum factorNum) {
		factorNum.struct = Tab.intType;    
	}
	
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}
	
	public void visit(FactorExpr factorExpr) { // LPAREN Expression:e RPAREN
		factorExpr.struct = factorExpr.getExpression().struct;
	}
	
	public void visit(FactorBool factorBool) { 
		factorBool.struct = boolType;
	}
	
	public void visit(FactorDesignator factorDesignator) { // Designator:d
		 factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}
	
	public void visit(FactorNew factorNew) {  
		if(factorNew.getExpression().struct != Tab.intType) {
    		report_error("Izraz u zagradama [] mora biti int", factorNew);        	
    		factorNew.struct = Tab.noType;
    		return;
    	}
		// treba array da prosledjuje!
		factorNew.struct = new Struct(Struct.Array, factorNew.getType().struct);
	}
	
	public void visit(FactorFuncCall factorFuncCall) { // MethodName:m LPAREN ActualParams:a RPAREN ---> (MethodName) Designator:d;
		if(factorFuncCall.getMethodName().getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Ime "+factorFuncCall.getMethodName().getDesignator().obj.getName() + " ne predstavlja funkciju", factorFuncCall);        	    		
			factorFuncCall.struct = Tab.noType;
			return;
		}
		// pravi parametri i njihov broj
		List<Struct> realArgs = allActParamsStack.pop(); // STA AKO je prosledjen arg poziv metode??? za sad me briga za to
    	int actArgsCnt = realArgs.size();
    	
    	// formalni argumenti i njihov broj
    	List<Obj> formalArgs = new ArrayList<Obj>(); 
    	Collection<Obj> locals = factorFuncCall.getMethodName().getDesignator().obj.getLocalSymbols();
    	int counter = factorFuncCall.getMethodName().getDesignator().obj.getLevel();
    	
    	// u listu se ubacuju samo formalni arg metode, ne i lokalne promenljive
    	for(Obj o: locals) {
    		if(counter == 0) break;
    		formalArgs.add(o);
    		counter--;
    	}
    	
    	// provera broja form args
    	if(actArgsCnt != factorFuncCall.getMethodName().getDesignator().obj.getLevel()) {
    		report_error("Broj formalnih i stvarnih argumenata nije isti", factorFuncCall);
    		factorFuncCall.struct = Tab.noType;
    		return;
    	}
    	
    	// provera kompatibilnosti
    	counter = factorFuncCall.getMethodName().getDesignator().obj.getLevel();
    	for(int i = 0; i < counter; i++) {
    		if(!realArgs.get(i).assignableTo(formalArgs.get(i).getType())){
    			if(realArgs.get(i).getKind() == 5 && formalArgs.get(i).getType().getKind() == 5) {
					// oba su tipa bool, ok
					continue;
    			}
    			report_error("Argument nije dobrog tipa! Prosledjeno: "+getKindType(realArgs.get(i)) + ". Treba: " + getKindType(formalArgs.get(i).getType()), factorFuncCall);
    			factorFuncCall.struct =  Tab.noType;
    			return;
    		}   
    	}
    	// vracamo tip povratne vrednosti fje:
    	report_info("Poziv funkcije "+factorFuncCall.getMethodName().getDesignator().obj.getName()+printObjNode(factorFuncCall.getMethodName().getDesignator().obj), factorFuncCall);
    	factorFuncCall.struct = factorFuncCall.getMethodName().getDesignator().obj.getType();
	}
	
	/* ============================================== CondFact ============================================== */	
	public void visit(CondFactRelop condFactRelop) { 
		if(!condFactRelop.getExpression().struct.compatibleWith(condFactRelop.getExpression1().struct)) {
    		report_error("Sabirci moraju biti kompatibilni ", condFactRelop);        	
    		
    		return;
    	}
		if(condFactRelop.getExpression().struct.getKind() == Struct.Array || condFactRelop.getExpression1().struct.getKind() == Struct.Array) {
	    	if(!currRelOp.equals(RelationalOp.EQUAL) && !currRelOp.equals(RelationalOp.NEQUAL)) {    		
		       	report_error("Nizovi se mogu porediti samo sa == ili !=", condFactRelop);  	    		
	    	}    		
	    }
	} 
	
	public void visit(CondFactExpression condFactExpression) {
		if(condFactExpression.getExpression().struct.getKind() != 5) {
    		report_error("Condition mora biti bool tipa ", condFactExpression);        	
    	}
	} 
	
	/* ============================================== Terms ============================================== */	
	public void visit(OneFactor oneFactor) {
		oneFactor.struct = oneFactor.getFactor().struct;    	
	}
	
	public void visit(MoreFactors moreFactors) {
		if(moreFactors.getFactor().struct != Tab.intType || moreFactors.getTerm().struct != Tab.intType) {
    		report_error("Tip cinilaca treba da bude broj", moreFactors);        	
    		moreFactors.struct = Tab.noType;
    		return;
    	}
		moreFactors.struct = Tab.intType;
	}
	 
	/* ============================================== Expressions ============================================== */	
	public void visit(OneTermExpr oneTermExpr) {
		oneTermExpr.struct = oneTermExpr.getTerm().struct;    	
	}
	
	public void visit(NegTermExpr negTermExpr) {
		if(negTermExpr.getTerm().struct != Tab.intType) {
    		report_error("Promenljiva mora biti broj", negTermExpr);        	
    		negTermExpr.struct = Tab.noType;
    		return;
    	}
		negTermExpr.struct = Tab.intType; 	
	}
	
	public void visit(ExpressionList expressionList) {
		if(!expressionList.getExpression().struct.compatibleWith(expressionList.getTerm().struct)) {
    		report_error("Sabirci moraju biti kompatibilni ", expressionList);        	
    		expressionList.struct = Tab.noType;
    		return;
    	}
    	if(expressionList.getExpression().struct != Tab.intType || expressionList.getTerm().struct != Tab.intType) {
    		report_error("Sabirci trebaju biti tipa int", expressionList);        	
    		expressionList.struct = Tab.noType;
    		return;
    	}
    	expressionList.struct = Tab.intType;
	}
	 
	/* ============================================== Designator ============================================== */
	public void visit(DesignatorIdent designatorIdent) { // IDENT:name
		String name = designatorIdent.getName();
		// ako se iz metode u namespace-u zove metoda iz istog tog namespace-a: poziva se bez scope-a, ali se sa njim pretrazuje u tabeli simbola
		if(currentMethod != null && inNamespace && Tab.find(namespaceName+"::"+name).getKind() == Obj.Meth) {
			name = namespaceName+"::"+name;
		}
		Obj obj = Tab.find(name);
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + designatorIdent.getLine()+ ": ime "+designatorIdent.getName()+" nije deklarisano! ", null);
		}
		if(Tab.find(name).getKind() != Obj.Meth) { 
			// koriscenje promenljive ili konstante
			String tip = "";
			if(Tab.find(name).getKind() == Obj.Var) tip = "promenljive";
			if(Tab.find(name).getKind() == Obj.Con) tip = "konstante";
			if(currentMethod != null) {
				if(Tab.currentScope.findSymbol(name) != null) {
					if(Tab.currentScope.findSymbol(name).getAdr() < currentMethod.getLevel()) { 
					// to je formalni argument te metode
						tip = "formalnog argumenta metode " + currentMethod.getName();
					}
				}
			}
			report_info("Koriscenje "+tip+" "+name+printObjNode(obj), designatorIdent); 
		}
		designatorIdent.obj = obj;
		// printObjNode(designatorIdent.obj);
	}
	
	public void visit(DesignatorScopedIdent designatorScopedIdent) { // IDENT:scope COLON COLON IDENT:name
		String name = designatorScopedIdent.getScope() + "::" + designatorScopedIdent.getName();
		Obj obj = Tab.find(name);
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + designatorScopedIdent.getLine()+ ": ime "+name+" nije deklarisano! ", null);
		}
		if(Tab.find(name).getKind() != Obj.Meth) { // DODATO************************
			// koriscenje promenljive ili konstante
			String tip = "";
			if(Tab.find(name).getKind() == Obj.Var) tip = "promenljive";
			if(Tab.find(name).getKind() == Obj.Con) tip = "konstante";
			report_info("Koriscenje "+tip+" "+name+printObjNode(obj), designatorScopedIdent);
		}
		designatorScopedIdent.obj = obj;
	}
	
	public void visit(DesignatorArrayIdent designatorArrayIdent) { // Designator LSQBRACE Expression RSQBRACE
		// PRISTUP ELEMENTU NIZA
		if(designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getType().getKind() != Struct.Array) {
    		report_error("Promenjiva " + designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getName() + " mora biti niz" , designatorArrayIdent);        	
    		designatorArrayIdent.obj = Tab.noObj;
        	return;
    	}	
		if(designatorArrayIdent.getExpression().struct != Tab.intType) {
			report_error("Izraz unutar [] mora biti tipa int" , designatorArrayIdent);        	
    		designatorArrayIdent.obj = Tab.noObj;
        	return;
		}
		// treba li ovaj deo?
		Obj obj = Tab.find(designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getName());
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + designatorArrayIdent.getLine()+ ": ime "+designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getName()+" nije deklarisano! ", null);
		}
		report_info("Pristup elementu niza "+designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getName()+printObjNode(obj), designatorArrayIdent);
		
		// slanje tipa elemenata niza a ne niz!!!!!!!!!!
		String name = designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getName();
		Struct s = designatorArrayIdent.getDesignatorArrayName().getDesignator().obj.getType().getElemType();
//		report_info("tip elem niza "+designatorArrayIdent.getDesignator().obj.getType().getElemType(), designatorArrayIdent);
		designatorArrayIdent.obj = new Obj(Obj.Elem, name, s);
	}
	 
	/* ============================================== DesignatorStatement ============================================== */	
	public void visit(DesignatorInc designatorInc) { // Designator INC
		Obj obj = Tab.find(designatorInc.getDesignator().obj.getName());		 
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Ime "+obj.getName()+" mora biti promenljiva ili element niza! ", designatorInc);
		}
		Struct type = designatorInc.getDesignator().obj.getType(); 
		if(type != Tab.intType) {
			report_error("Samo designator tipa int se moze inkrementirati", designatorInc);
		}
	}
	
	public void visit(DesignatorDec designatorDec) { // Designator DEC
		Obj obj = Tab.find(designatorDec.getDesignator().obj.getName());		 
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Ime "+obj.getName()+" mora biti promenljiva ili element niza! ", designatorDec);
		}
		Struct type = designatorDec.getDesignator().obj.getType(); 
		if(type != Tab.intType) {
			report_error("Samo designator tipa int se moze dekrementirati", designatorDec);
		}
	}
	
	public void visit(DesignatorEqual designatorEqual) { // Designator:dest EQUAL Expression:e
		Obj obj = Tab.find(designatorEqual.getDesignator().obj.getName());		 
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) { // ovaj prvi deo je dobar
			report_error("Ime "+obj.getName()+" mora biti promenljiva ili element niza! ", designatorEqual);
		} 
		if(obj.getType().getKind() == 5 && designatorEqual.getExpression().struct.getKind() == 5) {
			// oba su bool, ok
			return;
			//report_error("Izraz unutar READ naredbe mora biti int, char ili bool tipa", designatorEqual);
		}
		if (!designatorEqual.getExpression().struct.compatibleWith(designatorEqual.getDesignator().obj.getType())) {
			report_error("Tip izraza koji se dodeljuje " + designatorEqual.getExpression().struct.getKind() + " nije kompatibilan sa promenljivom " + designatorEqual.getDesignator().obj.getKind(), designatorEqual);
		} 
	}
	
	public void visit(DesignatorWParams designatorWParams) { // Designator LPAREN ActualParams RPAREN
		if(designatorWParams.getMethodName().getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Ime "+designatorWParams.getMethodName().getDesignator().obj.getName() + " ne predstavlja funkciju", designatorWParams);        	    		
			return;
		}
		// pravi parametri i njihov broj
		List<Struct> realArgs = allActParamsStack.pop();
    	int actArgsCnt = realArgs.size();
    	
    	// formalni argumenti i njihov broj
    	List<Obj> formalArgs = new ArrayList<Obj>(); 
    	Collection<Obj> locals = designatorWParams.getMethodName().getDesignator().obj.getLocalSymbols();
    	int counter = designatorWParams.getMethodName().getDesignator().obj.getLevel();
    	
    	// u listu se ubacuju samo formalni arg metode, ne i lokalne promenljive
    	for(Obj o: locals) {
    		if(counter == 0) break;
    		formalArgs.add(o);
    		counter--;
    	}
    	
    	// provera broja form args
    	if(actArgsCnt != designatorWParams.getMethodName().getDesignator().obj.getLevel()) {
    		report_error("Broj formalnih i stvarnih argumenata nije isti", designatorWParams);
    		return;
    	}
    	
    	// provera kompatibilnosti
    	counter = designatorWParams.getMethodName().getDesignator().obj.getLevel();
    	for(int i = 0; i < counter; i++) {
    		if(!realArgs.get(i).assignableTo(formalArgs.get(i).getType())){
    			if(realArgs.get(i).getKind() == 5 && formalArgs.get(i).getType().getKind() == 5) {
					// oba su tipa bool, ok
					continue;
    			}
    			report_error("Argument nije dobrog tipa! Prosledjeno: "+getKindType(realArgs.get(i)) + ". Treba: " + getKindType(formalArgs.get(i).getType()), designatorWParams);
    			return;
    		}   
    	}
    	report_info("Poziv funkcije "+designatorWParams.getMethodName().getDesignator().obj.getName()+printObjNode(designatorWParams.getMethodName().getDesignator().obj), designatorWParams);
	}
	
	public void visit(MethodName methodName) { /* pri pozivu */
		methodName.obj = methodName.getDesignator().obj;
		allActParamsStack.push(new ArrayList<Struct>());
	}

	public void visit(DesignatorArray designatorArray) { 
		// LSQBRACE DesignatorArrayList MUL Designator RSQBRACE EQUAL Designator
		// [a,,*a] = d;
		
		// ovaj desno mora biti niz		 
		if(designatorArray.getDesignator1().obj.getType().getKind() != Struct.Array) {
			report_error("Designator sa desne strane znaka = "+designatorArray.getDesignator1().obj.getName()+" nije niz", designatorArray);
		}
		// ovaj posle * isto mora biti niz		 
		if(designatorArray.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Designator "+designatorArray.getDesignator().obj.getName()+" na poziciji "+designs.size()+" nije niz", designatorArray);
		}
		// svi ostali moraju biti var ili niz 	 
		for(int i = 0; i < designs.size(); i++) {
			if(designs.get(i).getType().getKind() != Struct.Array  /*&&designs.get(i).getKind() != Obj.Var*/
					&& designs.get(i).getKind() == Obj.Con
					&& designs.get(i).getType().getKind() != 1
					&& designs.get(i).getType().getKind() != 2
					&& designs.get(i).getType().getKind() != 5) { // int, char, bool
				report_error("Designator "+designs.get(i).getType().getKind()+" "+designs.get(i).getName()+" na poziciji "+(i+1)+" nije ni niz ni promenljiva", designatorArray);
			}
		}		
		// mul designator i desni moraju imati kompatibilne tipove	
		if(!designatorArray.getDesignator().obj.getType().compatibleWith(designatorArray.getDesignator1().obj.getType())) { 
			report_error("nizovi nisu kompatibilni", designatorArray);
		}
		// svi ostali levo moraju biti komp sa ovim desno
		for(int i = 0; i < designs.size(); i++) {
			if(designs.get(i).getType().getKind() == Struct.Array) {
				if(!designs.get(i).getType().compatibleWith(designatorArray.getDesignator1().obj.getType())) { 
					report_error("Designator "+designs.get(i).getName()+" na poziciji "+(i+1)+" nije kompatibilan sa tipom elemenata niza sa desne strane = "+designatorArray.getDesignator1().obj.getName(), designatorArray);
				}							
			} else if(designs.get(i).getKind() == Obj.Var) {
				if(!designs.get(i).getType().compatibleWith(designatorArray.getDesignator1().obj.getType().getElemType())) {
					report_error("Designator "+designs.get(i).getName()+" na poziciji "+(i+1)+" nije kompatibilan sa tipom elemenata niza sa desne strane = "+designatorArray.getDesignator1().obj.getName(), designatorArray);
				}	
			}
		}	
		designs.clear();
	}
	
	public void visit(HasDesign hasDesign) { 
		// Designator:d
		designs.add(hasDesign.getDesignator().obj);
		//report_info("designatori:"+designs, hasDesign);
	}
	
	/* ============================================== ActualParameters ============================================== */	
	public void visit(MultipleActParams multipleActParams) {
		allActParamsStack.peek().add(multipleActParams.getExpression().struct);
	}
	public void visit(SingleActParam singleActParam) {
		allActParamsStack.peek().add(singleActParam.getExpression().struct);
	}
	
	/* ============================================== Statements ============================================== */
	public void visit(PrintStatement printStatement) { 
		Struct expr = printStatement.getExpression().struct;
		if(expr != Tab.intType && expr != Tab.charType && expr != boolType) {
			report_error("Argument print funkcije mora biti int, char ili bool tipa", printStatement);
		}
		report_info("Poziv metode print()", printStatement);
	}
	
	public void visit(PrintWidthStatement printWidthStatement) { 
		Struct expr = printWidthStatement.getExpression().struct;
		if(expr != Tab.intType && expr != Tab.charType && expr != boolType) {
			report_error("Argument print funkcije mora biti int, char ili bool tipa", printWidthStatement);
		}
		report_info("Poziv metode print()", printWidthStatement);
	}
	
	public void visit(ReadStatement readStatement) { 
		Obj obj = Tab.find(readStatement.getDesignator().obj.getName());		 
		if(obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Greska u pozivu funkcije read: ime "+readStatement.getDesignator().obj.getName()+" mora biti promenljiva ili element niza! ", readStatement);
		}
		Struct type = readStatement.getDesignator().obj.getType(); 
		if(type != Tab.intType && type != Tab.charType && type.getKind() != 5) {
			report_error("Argument read funkcije mora biti tipa int, char ili bool ", readStatement);
		}
		report_info("Poziv metode read() ", readStatement);
	}
 	
	/* ============================================== BreakAndContinue ============================================== */
	public void visit(BreakStatement breakStatement) {
		if(!inForLoop) {
			report_error("Break se ne moze nalaziti van for petlje", breakStatement);
		}
	}
	
	public void visit(ContinueStatement continueStatement) {
		if(!inForLoop) {
			report_error("Continue se ne moze nalaziti van for petlje", continueStatement);
		}
	}
	
	/* ============================================== ForLoop ============================================== */
	public void visit(ForLoopStatement forLoopStatement) {
		inForLoop = false;
	}
	
	public void visit(StartOfForLoop startOfForLoop) {
		inForLoop = true;
	}
	
	/* ============================================== RelOps ============================================== */
	public void visit(Same same) {
		currRelOp = RelationalOp.EQUAL;
	}
	
	public void visit(NotSame notSame) {
		currRelOp = RelationalOp.NEQUAL;
	}
	
	public void visit(Greater greater) {
		currRelOp = RelationalOp.GREATER;
	}
	
	public void visit(Less less) {
		currRelOp = RelationalOp.LESS;
	}
	
	public void visit(GreaterEq greaterEq) {
		currRelOp = RelationalOp.GREATEQ;
	}
	
	public void visit(LessEq lessEq) {
		currRelOp = RelationalOp.LESSEQ;
	}
	/* ====================================== modifs ====================================== */
	public void visit(MonkeyStatement monkeyStatement) { // DesignatorArrayName:name MONKEY NUMBER
		Obj obj = Tab.find(monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName());
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + monkeyStatement.getLine()+ ": ime "+monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName()+" nije deklarisano! ", null);
		}
		if(monkeyStatement.getDesignatorArrayName().getDesignator().obj.getType().getKind() != Struct.Array) {
    		report_error("Promenjiva " + monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName() + " mora biti niz" , monkeyStatement);        	
    		monkeyStatement.getDesignatorArrayName().getDesignator().obj = Tab.noObj;
        	return;
    	}	
		monkeyStatement.struct = Tab.intType;   
	}
	
	public void visit(ArrAssign arrAssign) { // DesignatorArrayName:name LSQBRACE expression COMMA expression RSQBRACE
		Obj obj = Tab.find(arrAssign.getDesignatorArrayName().getDesignator().obj.getName());
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + arrAssign.getLine()+ ": ime "+arrAssign.getDesignatorArrayName().getDesignator().obj.getName()+" nije deklarisano! ", null);
			return;
		}
		if(arrAssign.getDesignatorArrayName().getDesignator().obj.getType().getKind() != Struct.Array) {
    		report_error("Promenjiva " + arrAssign.getDesignatorArrayName().getDesignator().obj.getName() + " mora biti niz" , arrAssign);        	
    		arrAssign.getDesignatorArrayName().getDesignator().obj = Tab.noObj;
        	return;
		}
	}
	
	public void visit(ArrAccess arrAccess) { // DesignatorArrayName:name LSQBRACE expression COMMA expression RSQBRACE
		arrAccess.struct = Tab.intType;
	}
	
	// (ArrAccess) HASH DesignatorArrayName:name LSQBRACE Expression:e RSQBRACE br preistu[ps
	
	/*
	public void visit(FactorArrMod factorArrMod) { //  DesignatorArrayName:name LSQBRACE NUMBER:ind RSQBRACE MONKEY NUMBER:num
		Obj obj = Tab.find(monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName());
		if (obj == Tab.noObj) { 
			report_error("Greska na liniji " + monkeyStatement.getLine()+ ": ime "+monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName()+" nije deklarisano! ", null);
		}
		if(monkeyStatement.getDesignatorArrayName().getDesignator().obj.getType().getKind() != Struct.Array) {
    		report_error("Promenjiva " + monkeyStatement.getDesignatorArrayName().getDesignator().obj.getName() + " mora biti niz" , monkeyStatement);        	
    		monkeyStatement.getDesignatorArrayName().getDesignator().obj = Tab.noObj;
        	return;
    	}	
		monkeyStatement.struct = Tab.intType;   
	}
	*/
}
