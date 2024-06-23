package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
//import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	public class LocalVarsVisitor extends VisitorAdaptor {

		int count;
		
		public int getCount(){
			return count;
		}
		
		public void visit(VarSingleSemi varSingleSemi){
			count++;
		}
		public void visit(LastVarDecl lastVarDecl){
			count++;
		}
		public void visit(VarCommaSeparated varCommaSeparated){
			count++;
		}
		public void visit(MoreVarDecl moreVarDecl){
			count++;
		}
	}

	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(PrintStatement printStatement) {  	
		// sta se ocekuje na steku? val, width
		// expression je vec stavljeno na stek (po sintaksnom pravilu expr se vec evaluiralo)
		// jos samo sirina polja
		if(printStatement.getExpression().struct == Tab.intType) {
			Code.loadConst(5); //  sirina? toliko treba za int
			Code.put(Code.print); // do
		} else if(printStatement.getExpression().struct == Tab.charType) {
			Code.loadConst(1); 
			Code.put(Code.bprint);
		} else {
			// bool
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}
	
	public void visit(PrintWidthStatement printWidthStatement) { 		
		Code.loadConst(printWidthStatement.getW()); // sirina je zadata, ne mora na osnovu tipa expr
		
		if(printWidthStatement.getExpression().struct == Tab.intType) {
 			Code.put(Code.print);
		} else if(printWidthStatement.getExpression().struct == Tab.charType) {
 			Code.put(Code.bprint);
		} else {
  			Code.put(Code.print);
		}
	}
	
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);			
		} else {
			Code.put(Code.read);
		}		
		Code.store(readStatement.getDesignator().obj);
	}
	
	public void visit(FactorNum factorNum) { 		
		Obj cnst = Tab.insert(Obj.Con, "immed", factorNum.struct);
		cnst.setLevel(0);
		cnst.setAdr(factorNum.getN()); // vr constante
		Code.load(cnst); // stavlja se obj cvor
	}
	
	public void visit(FactorChar factorChar) { 
		Obj cnst = Tab.insert(Obj.Con, "immed", Tab.charType);
		cnst.setLevel(0);
		cnst.setAdr(factorChar.getC());
		Code.load(cnst);
	}
	
	public void visit(FactorBool factorBool) { // ne ispisuje true i false nego 1 i 0 inace RADI
		Obj cnst = Tab.insert(Obj.Con, "immed", new Struct(Struct.Bool));
		cnst.setLevel(0);
		if(factorBool.getB() == true) {
			cnst.setAdr(1);
		} else {
			cnst.setAdr(0);
		}
		Code.load(cnst);
	}
	
	public void visit(MethodTypeName methodTypeName) { 		
		if("main".equalsIgnoreCase(methodTypeName.getMethodName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		
		SyntaxNode methodNode = methodTypeName.getParent();
		
		LocalVarsVisitor varCnt = new LocalVarsVisitor();
		methodNode.traverseTopDown(varCnt);
		
		//FormParamCounter fpCnt = new FormParamCounter();
		//methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(0); //Code.put(fpCnt.getCount());
		//Code.put(fpCnt.getCount() + varCnt.getCount());
		Code.put( varCnt.getCount());
	}
	
	public void visit(MethodDeclaration methodDeclaration){		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorEqual designatorEqual){		
		Code.store(designatorEqual.getDesignator().obj);
	}
	
	public void visit(DesignatorIdent designatorIdent){	
		SyntaxNode parent = designatorIdent.getParent();
		
		// promenljiva koja ucestvuje u izrazima
		/*if(DesignatorEqual.class == parent.getClass() ||
				FactorDesignator.class == parent.getClass() ||
				FactorFuncCall.class == parent.getClass()) {
			Code.load(designatorIdent.obj);
		}*/
		// ako designator ne potice iz te smene, njemu se dodeljuje vrednost i u tom slucaju ne smesta se na stek
		if(FactorDesignator.class == parent.getClass()//||
				//DesignatorInc.class == parent.getClass() ||
				//DesignatorDec.class == parent.getClass()
				) { 
			Code.load(designatorIdent.obj);
		}
	}
	
	public void visit(DesignatorScopedIdent designatorScopedIdent){	
		SyntaxNode parent = designatorScopedIdent.getParent();
		
		// ako designator potice iz te smene, njemu se dodeljuje vrednost i u tom slucaju ne smesta se na stek
		if(FactorDesignator.class == parent.getClass()) { 
			Code.load(designatorScopedIdent.obj);
		}
	}
	
	public void visit(DesignatorArrayIdent designatorArrayIdent){		
		// na steku su adr i index
		Obj temp = new Obj(Obj.Var, "baba", Tab.intType, 0, 0);
		Code.put(Code.dup2);
		Code.store(temp); // index
		
		Code.put(Code.dup);
		Code.put(Code.arraylength); 
		Code.loadConst(2);
		Code.put(Code.div);
		Code.load(temp);
		Code.put(Code.add);
		
		Code.store(temp); // index polja za brojanje
		// adr
		Code.put(Code.dup);
		Code.load(temp);
		Code.put(Code.dup2);
		
		Code.put(Code.aload);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store);
		
		Code.put(Code.pop);
		// adr 
		
		SyntaxNode parent = designatorArrayIdent.getParent();
		
		// ako designator potice iz te smene, njemu se dodeljuje vrednost i u tom slucaju ne smesta se na stek
		if(FactorDesignator.class == parent.getClass()) { 
			Code.load(designatorArrayIdent.obj); // adr niza
		}
	}
	
	public void visit(DesignatorArrayName designatorArrayName){		
		Code.load(designatorArrayName.getDesignator().obj); // adr niza
	}
	
	public void visit(NegTermExpr negTermExpr) {		
		Code.put(Code.neg); // negira vrednost na vrhu steka, ostaje na steku
	}
	
	public void visit(ExpressionList expressionList) {		
		if(expressionList.getAddop() instanceof Add) {// addop: uzme dve stvari sa steka i ostavi 1 -> rezultat
			Code.put(Code.add); 
		} else {
			Code.put(Code.sub); 
		}
	}
	
	public void visit(DesignatorInc designatorInc) {		
		if(designatorInc.getDesignator().obj.getKind() == Obj.Var) {	 
			Code.load(designatorInc.getDesignator().obj);	// 
		} else if(designatorInc.getDesignator().obj.getKind() == Obj.Elem) { // n[9]++;
			// estack: adr, index 
			Code.put(Code.dup2);  // zbog store-a: treba mu adr, index, val
			// estack: adr, index, adr, index
			Code.load(designatorInc.getDesignator().obj); // uzelo: adr, index; umesto toga stavljeno: val => SAD: adr, ind, val 
		}
		Code.loadConst(1); // stek: adr, ind, val, 1
		Code.put(Code.add); // stek: adr, ind, val+1
		Code.store(designatorInc.getDesignator().obj); // dodeljeno promenljivoj
	}
	
	public void visit(DesignatorDec designatorDec) {					
		if(designatorDec.getDesignator().obj.getKind() == Obj.Var) { 
			Code.load(designatorDec.getDesignator().obj);	// 
		} else if(designatorDec.getDesignator().obj.getKind() == Obj.Elem) { // n[9]--;
			// estack: adr, index 
			Code.put(Code.dup2);  // zbog store-a: treba mu adr, index, val
			Code.load(designatorDec.getDesignator().obj); // uzelo: adr, index; umesto toga stavljeno: val => SAD: adr, ind, val 
		}
		Code.loadConst(1); // stek: adr, ind, val, 1
		Code.put(Code.sub); // stek: adr, ind, val-1
		Code.store(designatorDec.getDesignator().obj); // dodeljeno promenljivoj
	}
	
	public void visit(MoreFactors moreFactors) {			
		if(moreFactors.getMulop() instanceof Mul) { 
			Code.put(Code.mul); 
		} else if(moreFactors.getMulop() instanceof Div) {
			Code.put(Code.div); 
		} else { // Mod
			Code.put(Code.rem);
		}
	}
	
	public void visit(FactorNew factorNew) { // (FactorNew) NEW_ Type:t LSQBRACE Expression:e RSQBRACE		
		// pravljenje niza: treba duzina niza i to je vec na steku
		// NEWARRAY B - b=0 ili 1 0-chars, 1-ostalo
		Code.loadConst(2);
		Code.put(Code.mul);
		
		Code.put(Code.newarray);
		if(factorNew.struct.getElemType() == Tab.charType) {		
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	public void visit(FactorDesignator factorDesignator) { 
		//Code.load(factorDesignator.getDesignator().obj);
	}
	
	public void visit(DesignatorWParams designatorWParams) { 
		Obj method = designatorWParams.getMethodName().getDesignator().obj;
		if("len".equals(method.getName())) {
			Code.put(Code.arraylength);
		}
	}
	
	public void visit(FactorFuncCall factorFuncCall) {  
		Obj method = factorFuncCall.getMethodName().getDesignator().obj;
		if("len".equals(method.getName())) {
			Code.put(Code.arraylength);
		}
	}
	
	public void visit(ReturnStatementExpr returnStatementExpr) { 
		// samo void main!
		Code.put(Code.trap);
	}
	
	
	
	/*========================================== IfElse ==========================================*/
	
	
	// Code.pc - 2 => pamcenje adrese koju treba patchovati
	
	List<Integer> dstAdrOrPatch= new ArrayList<Integer>(); // gde se skace ako su svi false	
	List<Integer> dstAdrAndPatch = new ArrayList<Integer>(); // gde se skace ako je barem 1 TACAN????
	List<Integer> dstAdrThenPatch = new ArrayList<Integer>(); // skok nakon celog if else na kraju then grane
	
	// AND 
	public void visit(CondFactExpression condFactExpression) { 
		// ovi su cinioci za AND, najdublje su u stablu => single
		Code.loadConst(1); // 1 je true
		// bice namesteno pomocu fixup: prosledi se adr za patchovanje, postavlja ga na trenutni pc
		Code.putFalseJump(Code.eq, 0); // poredi sa 1...
		// and je false => bice skok..
		if(condFactExpression.getParent() instanceof ForCondFact) {
			//afterCond = Code.pc - 2;
		} else { // ZA IF
			dstAdrAndPatch.add(Code.pc - 2); // adr za patchovanje
		// ako je false idemo na else inace u then
		}
		
	}
	
	public void visit(CondFactRelop condFactRelop) { // izraz
		// false: skok na else
		// jos ne znamo dje je else pa cemo to posle (i ne znamo da li ide then ili else)
		// sad cuvamo adresu gde kasnije radimo patch, kad naidjemo na else
		if(condFactRelop.getRelop() instanceof Same) {
			Code.putFalseJump(Code.eq, 0); 			
		} else if(condFactRelop.getRelop() instanceof NotSame) {
			Code.putFalseJump(Code.ne, 0); 			
		} else if(condFactRelop.getRelop() instanceof Less) {
			Code.putFalseJump(Code.lt, 0); 			
		} else if(condFactRelop.getRelop() instanceof Greater) {
			Code.putFalseJump(Code.gt, 0); 			
		} else if(condFactRelop.getRelop() instanceof LessEq) {
			Code.putFalseJump(Code.le, 0); 
		} else if(condFactRelop.getRelop() instanceof GreaterEq) {
			Code.putFalseJump(Code.ge, 0); 			
		}
		if(condFactRelop.getParent() instanceof ForCondFact) {
			//afterCond = Code.pc - 2; // TO PATCH
		} else { // ZA IF
			dstAdrAndPatch.add(Code.pc - 2); // cuva za patch
		// znace se kad se obradi taj ili blok????
		}
		
	}
	
	
	// CONDITION  
	public void visit(NextToLastOr nextToLastOr) { // predzadnji or block 
		Code.putJump(0); 
		dstAdrOrPatch.add(Code.pc - 2); 
		// netacan and? provera ve sledeci or????
		for(int adrToPatch: dstAdrAndPatch) {
			Code.fixup(adrToPatch);
		}
		dstAdrAndPatch.clear(); 
	}
	
	public void visit(EndOfIfCondition endOfIfCondition) { 
		for(int adrToPatch: dstAdrOrPatch) {
			Code.fixup(adrToPatch);
		}
		dstAdrOrPatch.clear();  
	}
	
	public void visit(AfterIf afterIf) { 
		// kraj ifa, ako ima else treba da ga bezuslovno preskoci
		if(afterIf.getParent() instanceof IfElseStatement) {
			Code.putJump(0); 		
			dstAdrThenPatch.add(Code.pc - 2); 
		}
		// ako je uslov bio netacan ovde se skace (pocetak else)
		for(int adrToPatch: dstAdrAndPatch) {
			Code.fixup(adrToPatch);
		}
		dstAdrAndPatch.clear();  		
	}
	
	public void visit(IfElseStatement ifElseStatement) {
		for(int adrToPatch: dstAdrThenPatch) { // gde skace nakon then ifa
			Code.fixup(adrToPatch);
		}
		dstAdrThenPatch.clear();  
	}
	
	public void visit(MonkeyStatement monkeyStatement) {

		// u design. arr. name je vec ucitana adr niza
		// jos index
		Code.loadConst(monkeyStatement.getNum()); 
		Code.put(Code.aload);
		
		// drugi 
		Code.load(monkeyStatement.getDesignatorArrayName().getDesignator().obj); // adr niza
		// len niza - treba jos jedna adr niza
		Code.put(Code.dup);
		// na steku dve adr niza
		Code.put(Code.arraylength);
		// adr i len
		Code.loadConst(monkeyStatement.getNum()); 
		Code.put(Code.sub);
		// sad imamo index 
		//ucitamo drugi sabirak - elem niza
		Code.put(Code.aload);
		
		// saberemo 
		Code.put(Code.add);
		
		// rezultat je na steku
	}
	
	HashMap<String, Integer> labele = new HashMap<String, Integer>();
	HashMap<String, List<Integer>> gotos = new HashMap<String, List<Integer>>();
	
	// lista integera gde treba da se fixapuje adresa
	public void visit(Label label) {
		if(!labele.containsKey(label.getLabelName().getLabel())) {
			labele.put(label.getLabelName().getLabel(), Code.pc);
		}
		// ispravi u gotos ako ga ima
		if(gotos.containsKey(label.getLabelName().getLabel())) {
			for(int adr: gotos.get(label.getLabelName().getLabel())) Code.fixup(adr);
		}
		
	}
	public void visit(Goto go) {
		if(labele.containsKey(go.getLabelName().getLabel())) {
			Code.putJump(labele.get(go.getLabelName().getLabel()));
			// skaci na labelu lmao pa sta bi drugo 
		} else {
			Code.putJump(0);
			if(gotos.containsKey(go.getLabelName().getLabel())) {
				gotos.get(go.getLabelName().getLabel()).add(Code.pc - 2);
			} else {
				gotos.put(go.getLabelName().getLabel(), new ArrayList<>());
				gotos.get(go.getLabelName().getLabel()).add(Code.pc - 2);
			}
		}
	}
	
	public void visit(ArrAssign arrAssign) { // DesignatorArrayName:name LSQBRACE num COMMA num RSQBRACE

		Code.loadConst(arrAssign.getNum2());
		Code.load(arrAssign.getDesignatorArrayName().getDesignator().obj);
		Code.loadConst(arrAssign.getNum1());  
		Code.put(Code.aload); 
		// spreman stek za ucitavanje vr drugog u prvi             da se uradi astore????
		
		Code.load(arrAssign.getDesignatorArrayName().getDesignator().obj);
		Code.loadConst(arrAssign.getNum1());
		Code.load(arrAssign.getDesignatorArrayName().getDesignator().obj);
		Code.loadConst(arrAssign.getNum2());
		Code.put(Code.aload);  // val
		
		Code.put(Code.astore); 
		Code.put(Code.astore); 		
	}
	
	public void visit(ArrAccess arrAccess) {
		// adr index 
		Obj temp = new Obj(Obj.Var, "baba", Tab.intType, 0, 0);
		Code.store(temp); // index
		
		// adr 
		Code.put(Code.dup); // adr adr
		Code.put(Code.arraylength); // adr len 
		Code.loadConst(2);
		Code.put(Code.div); // adr len/2
		Code.load(temp);
		Code.put(Code.add); // index cnt ok
		
		//Code.store(temp); // index polja za brojanje		
		// adr 
		
		Code.put(Code.aload);	// val	
	}
	
	
	
/*
	// for loop
	
	
	int forCond = 0; // pocetak uslova - tu se skace uvek na kraju izvrsavanja i++: UVEK, posle: ili posle fora ili na forbody
	int forTail = 0; // tu se skace UVEK na kraju izvrsavanja jednog ciklusa petlje,      posle: UVEK na cond
	int forBody = 0; // tu: nakon provere uslova?                                         posle: na i++ UVEK
	
	int afterCond = 0; // toFixup...........
	int afterForLoop = 0; // kraj for petlje
	
	// start... and tail
	public void visit(SingleDesignator singleDesignator) {
		if(singleDesignator.getParent() instanceof DesignatorStatementsListHead) {
			forCond = Code.pc - 2;
			// ide na cond.. nema skoka
		} else {
			forBody = Code.pc - 2;
			// skok na cond
			Code.putJump(forCond); 	
		}
	}
	
	public void visit(NoDesignStatementListHead noDesignStatementList) {
		forCond = Code.pc - 2;
		// ide na cond, nema skoka
	}
	
	public void visit(NoDesignStatementListTail noDesignStatementList) {
		forBody = Code.pc - 2;
		// posle i++, ide na izvrsavanje cond
		Code.putJump(forCond);
	}
	
	// uslov********************
	public void visit(HasForCondFact hasForCondFact) {
		forTail = Code.pc - 2;
		// skok na pocetak for body ili na kraj?
		Code.putJump(afterCond);
	}
	
	public void visit(NoForCondFact noForCondFact) {
		forTail = Code.pc - 2;
		Code.putJump(forBody);
	}
	 
	// for body
	public void visit(StartOfForLoop startOfForLoop) { // ok
		//forBody = Code.pc - 2; ili ovde ili nakon i++? ili je svj
	}
	
	// end
	public void visit(ForLoopStatement forLoopStatement) { 
		afterForLoop = Code.pc - 2;
		Code.putJump(forTail);
	}
	*/
}