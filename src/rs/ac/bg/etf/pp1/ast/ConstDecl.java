// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl extends ConstDeclaration {

    private Type Type;
    private Constant Constant;
    private MoreConstants MoreConstants;

    public ConstDecl (Type Type, Constant Constant, MoreConstants MoreConstants) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.Constant=Constant;
        if(Constant!=null) Constant.setParent(this);
        this.MoreConstants=MoreConstants;
        if(MoreConstants!=null) MoreConstants.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public Constant getConstant() {
        return Constant;
    }

    public void setConstant(Constant Constant) {
        this.Constant=Constant;
    }

    public MoreConstants getMoreConstants() {
        return MoreConstants;
    }

    public void setMoreConstants(MoreConstants MoreConstants) {
        this.MoreConstants=MoreConstants;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(Constant!=null) Constant.accept(visitor);
        if(MoreConstants!=null) MoreConstants.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(Constant!=null) Constant.traverseTopDown(visitor);
        if(MoreConstants!=null) MoreConstants.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(Constant!=null) Constant.traverseBottomUp(visitor);
        if(MoreConstants!=null) MoreConstants.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Constant!=null)
            buffer.append(Constant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreConstants!=null)
            buffer.append(MoreConstants.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
