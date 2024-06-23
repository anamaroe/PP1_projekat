// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class VarCommaSeparated extends MoreVariableDecls {

    private MoreVariableDecls MoreVariableDecls;
    private String VarName;
    private Array Array;

    public VarCommaSeparated (MoreVariableDecls MoreVariableDecls, String VarName, Array Array) {
        this.MoreVariableDecls=MoreVariableDecls;
        if(MoreVariableDecls!=null) MoreVariableDecls.setParent(this);
        this.VarName=VarName;
        this.Array=Array;
        if(Array!=null) Array.setParent(this);
    }

    public MoreVariableDecls getMoreVariableDecls() {
        return MoreVariableDecls;
    }

    public void setMoreVariableDecls(MoreVariableDecls MoreVariableDecls) {
        this.MoreVariableDecls=MoreVariableDecls;
    }

    public String getVarName() {
        return VarName;
    }

    public void setVarName(String VarName) {
        this.VarName=VarName;
    }

    public Array getArray() {
        return Array;
    }

    public void setArray(Array Array) {
        this.Array=Array;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreVariableDecls!=null) MoreVariableDecls.accept(visitor);
        if(Array!=null) Array.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreVariableDecls!=null) MoreVariableDecls.traverseTopDown(visitor);
        if(Array!=null) Array.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreVariableDecls!=null) MoreVariableDecls.traverseBottomUp(visitor);
        if(Array!=null) Array.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarCommaSeparated(\n");

        if(MoreVariableDecls!=null)
            buffer.append(MoreVariableDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+VarName);
        buffer.append("\n");

        if(Array!=null)
            buffer.append(Array.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarCommaSeparated]");
        return buffer.toString();
    }
}
