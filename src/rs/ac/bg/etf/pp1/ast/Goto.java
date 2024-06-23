// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class Goto extends DesignatorStatement {

    private LabelName LabelName;

    public Goto (LabelName LabelName) {
        this.LabelName=LabelName;
        if(LabelName!=null) LabelName.setParent(this);
    }

    public LabelName getLabelName() {
        return LabelName;
    }

    public void setLabelName(LabelName LabelName) {
        this.LabelName=LabelName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LabelName!=null) LabelName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LabelName!=null) LabelName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LabelName!=null) LabelName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Goto(\n");

        if(LabelName!=null)
            buffer.append(LabelName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Goto]");
        return buffer.toString();
    }
}
