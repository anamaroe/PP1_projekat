// generated with ast extension for cup
// version 0.8
// 16/0/2024 20:25:5


package rs.ac.bg.etf.pp1.ast;

public class DesigntorMonkey extends DesignatorStatement {

    private MonkeyStatement MonkeyStatement;

    public DesigntorMonkey (MonkeyStatement MonkeyStatement) {
        this.MonkeyStatement=MonkeyStatement;
        if(MonkeyStatement!=null) MonkeyStatement.setParent(this);
    }

    public MonkeyStatement getMonkeyStatement() {
        return MonkeyStatement;
    }

    public void setMonkeyStatement(MonkeyStatement MonkeyStatement) {
        this.MonkeyStatement=MonkeyStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MonkeyStatement!=null) MonkeyStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MonkeyStatement!=null) MonkeyStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MonkeyStatement!=null) MonkeyStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigntorMonkey(\n");

        if(MonkeyStatement!=null)
            buffer.append(MonkeyStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigntorMonkey]");
        return buffer.toString();
    }
}
