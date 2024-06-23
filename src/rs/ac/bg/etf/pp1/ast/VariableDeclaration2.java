// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclaration2 extends ConstAndVarDeclarations {

    private ConstAndVarDeclarations ConstAndVarDeclarations;
    private VariableDeclarationList VariableDeclarationList;

    public VariableDeclaration2 (ConstAndVarDeclarations ConstAndVarDeclarations, VariableDeclarationList VariableDeclarationList) {
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.setParent(this);
        this.VariableDeclarationList=VariableDeclarationList;
        if(VariableDeclarationList!=null) VariableDeclarationList.setParent(this);
    }

    public ConstAndVarDeclarations getConstAndVarDeclarations() {
        return ConstAndVarDeclarations;
    }

    public void setConstAndVarDeclarations(ConstAndVarDeclarations ConstAndVarDeclarations) {
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
    }

    public VariableDeclarationList getVariableDeclarationList() {
        return VariableDeclarationList;
    }

    public void setVariableDeclarationList(VariableDeclarationList VariableDeclarationList) {
        this.VariableDeclarationList=VariableDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.accept(visitor);
        if(VariableDeclarationList!=null) VariableDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseTopDown(visitor);
        if(VariableDeclarationList!=null) VariableDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseBottomUp(visitor);
        if(VariableDeclarationList!=null) VariableDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclaration2(\n");

        if(ConstAndVarDeclarations!=null)
            buffer.append(ConstAndVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclarationList!=null)
            buffer.append(VariableDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclaration2]");
        return buffer.toString();
    }
}
