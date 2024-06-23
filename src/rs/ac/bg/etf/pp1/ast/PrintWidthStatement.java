// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class PrintWidthStatement extends Statement {

    private Expression Expression;
    private Integer w;

    public PrintWidthStatement (Expression Expression, Integer w) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.w=w;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w=w;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintWidthStatement(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+w);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintWidthStatement]");
        return buffer.toString();
    }
}
