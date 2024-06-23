// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class ConstDecls extends ConstAndVarDeclarations {

    private ConstAndVarDeclarations ConstAndVarDeclarations;
    private ConstDeclaration ConstDeclaration;

    public ConstDecls (ConstAndVarDeclarations ConstAndVarDeclarations, ConstDeclaration ConstDeclaration) {
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.setParent(this);
        this.ConstDeclaration=ConstDeclaration;
        if(ConstDeclaration!=null) ConstDeclaration.setParent(this);
    }

    public ConstAndVarDeclarations getConstAndVarDeclarations() {
        return ConstAndVarDeclarations;
    }

    public void setConstAndVarDeclarations(ConstAndVarDeclarations ConstAndVarDeclarations) {
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
    }

    public ConstDeclaration getConstDeclaration() {
        return ConstDeclaration;
    }

    public void setConstDeclaration(ConstDeclaration ConstDeclaration) {
        this.ConstDeclaration=ConstDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.accept(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseTopDown(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseBottomUp(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecls(\n");

        if(ConstAndVarDeclarations!=null)
            buffer.append(ConstAndVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclaration!=null)
            buffer.append(ConstDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecls]");
        return buffer.toString();
    }
}
