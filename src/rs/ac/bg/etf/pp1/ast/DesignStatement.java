// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class DesignStatement extends Statement {

    private DesignatorStatementStat DesignatorStatementStat;

    public DesignStatement (DesignatorStatementStat DesignatorStatementStat) {
        this.DesignatorStatementStat=DesignatorStatementStat;
        if(DesignatorStatementStat!=null) DesignatorStatementStat.setParent(this);
    }

    public DesignatorStatementStat getDesignatorStatementStat() {
        return DesignatorStatementStat;
    }

    public void setDesignatorStatementStat(DesignatorStatementStat DesignatorStatementStat) {
        this.DesignatorStatementStat=DesignatorStatementStat;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementStat!=null) DesignatorStatementStat.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementStat!=null) DesignatorStatementStat.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementStat!=null) DesignatorStatementStat.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignStatement(\n");

        if(DesignatorStatementStat!=null)
            buffer.append(DesignatorStatementStat.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignStatement]");
        return buffer.toString();
    }
}
