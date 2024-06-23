// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class NoMoreVarDecl extends VariableDeclarationList {

    private Type Type;
    private SingleVarDeclaration SingleVarDeclaration;

    public NoMoreVarDecl (Type Type, SingleVarDeclaration SingleVarDeclaration) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SingleVarDeclaration=SingleVarDeclaration;
        if(SingleVarDeclaration!=null) SingleVarDeclaration.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SingleVarDeclaration getSingleVarDeclaration() {
        return SingleVarDeclaration;
    }

    public void setSingleVarDeclaration(SingleVarDeclaration SingleVarDeclaration) {
        this.SingleVarDeclaration=SingleVarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(SingleVarDeclaration!=null) SingleVarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SingleVarDeclaration!=null) SingleVarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SingleVarDeclaration!=null) SingleVarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoMoreVarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleVarDeclaration!=null)
            buffer.append(SingleVarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoMoreVarDecl]");
        return buffer.toString();
    }
}
