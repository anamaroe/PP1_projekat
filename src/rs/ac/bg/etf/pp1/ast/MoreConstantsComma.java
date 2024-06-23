// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class MoreConstantsComma extends MoreConstants {

    private MoreConstants MoreConstants;
    private Constant Constant;

    public MoreConstantsComma (MoreConstants MoreConstants, Constant Constant) {
        this.MoreConstants=MoreConstants;
        if(MoreConstants!=null) MoreConstants.setParent(this);
        this.Constant=Constant;
        if(Constant!=null) Constant.setParent(this);
    }

    public MoreConstants getMoreConstants() {
        return MoreConstants;
    }

    public void setMoreConstants(MoreConstants MoreConstants) {
        this.MoreConstants=MoreConstants;
    }

    public Constant getConstant() {
        return Constant;
    }

    public void setConstant(Constant Constant) {
        this.Constant=Constant;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreConstants!=null) MoreConstants.accept(visitor);
        if(Constant!=null) Constant.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreConstants!=null) MoreConstants.traverseTopDown(visitor);
        if(Constant!=null) Constant.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreConstants!=null) MoreConstants.traverseBottomUp(visitor);
        if(Constant!=null) Constant.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstantsComma(\n");

        if(MoreConstants!=null)
            buffer.append(MoreConstants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Constant!=null)
            buffer.append(Constant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstantsComma]");
        return buffer.toString();
    }
}
