package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.LastVarDecl;
import rs.ac.bg.etf.pp1.ast.MoreVarDecl;
import rs.ac.bg.etf.pp1.ast.SingleFormalParam;
import rs.ac.bg.etf.pp1.ast.VarCommaSeparated;
import rs.ac.bg.etf.pp1.ast.VarSingleSemi;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(SingleFormalParam singleFormalParam){
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
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
}
