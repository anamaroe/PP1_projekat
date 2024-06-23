// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class MultipleDesignator extends ForLoopDesignatorStatements {

    private ForLoopDesignatorStatements ForLoopDesignatorStatements;
    private DesignatorStatement DesignatorStatement;

    public MultipleDesignator (ForLoopDesignatorStatements ForLoopDesignatorStatements, DesignatorStatement DesignatorStatement) {
        this.ForLoopDesignatorStatements=ForLoopDesignatorStatements;
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
    }

    public ForLoopDesignatorStatements getForLoopDesignatorStatements() {
        return ForLoopDesignatorStatements;
    }

    public void setForLoopDesignatorStatements(ForLoopDesignatorStatements ForLoopDesignatorStatements) {
        this.ForLoopDesignatorStatements=ForLoopDesignatorStatements;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleDesignator(\n");

        if(ForLoopDesignatorStatements!=null)
            buffer.append(ForLoopDesignatorStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleDesignator]");
        return buffer.toString();
    }
}
