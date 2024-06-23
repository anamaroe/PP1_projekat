// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class TypeNoScope extends Type {

    private String typeNameWithoutScope;

    public TypeNoScope (String typeNameWithoutScope) {
        this.typeNameWithoutScope=typeNameWithoutScope;
    }

    public String getTypeNameWithoutScope() {
        return typeNameWithoutScope;
    }

    public void setTypeNameWithoutScope(String typeNameWithoutScope) {
        this.typeNameWithoutScope=typeNameWithoutScope;
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
        buffer.append("TypeNoScope(\n");

        buffer.append(" "+tab+typeNameWithoutScope);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeNoScope]");
        return buffer.toString();
    }
}
