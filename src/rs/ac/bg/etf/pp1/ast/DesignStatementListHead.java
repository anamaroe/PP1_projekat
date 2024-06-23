// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class DesignStatementListHead extends DesignatorStatementsListHead {

    private ForLoopDesignatorStatements ForLoopDesignatorStatements;

    public DesignStatementListHead (ForLoopDesignatorStatements ForLoopDesignatorStatements) {
        this.ForLoopDesignatorStatements=ForLoopDesignatorStatements;
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.setParent(this);
    }

    public ForLoopDesignatorStatements getForLoopDesignatorStatements() {
        return ForLoopDesignatorStatements;
    }

    public void setForLoopDesignatorStatements(ForLoopDesignatorStatements ForLoopDesignatorStatements) {
        this.ForLoopDesignatorStatements=ForLoopDesignatorStatements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForLoopDesignatorStatements!=null) ForLoopDesignatorStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignStatementListHead(\n");

        if(ForLoopDesignatorStatements!=null)
            buffer.append(ForLoopDesignatorStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignStatementListHead]");
        return buffer.toString();
    }
}
