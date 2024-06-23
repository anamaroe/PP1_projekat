// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class TypeWScope extends Type {

    private String typeNameScope;
    private String typeNameWithScope;

    public TypeWScope (String typeNameScope, String typeNameWithScope) {
        this.typeNameScope=typeNameScope;
        this.typeNameWithScope=typeNameWithScope;
    }

    public String getTypeNameScope() {
        return typeNameScope;
    }

    public void setTypeNameScope(String typeNameScope) {
        this.typeNameScope=typeNameScope;
    }

    public String getTypeNameWithScope() {
        return typeNameWithScope;
    }

    public void setTypeNameWithScope(String typeNameWithScope) {
        this.typeNameWithScope=typeNameWithScope;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeWScope(\n");

        buffer.append(" "+tab+typeNameScope);
        buffer.append("\n");

        buffer.append(" "+tab+typeNameWithScope);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeWScope]");
        return buffer.toString();
    }
}
