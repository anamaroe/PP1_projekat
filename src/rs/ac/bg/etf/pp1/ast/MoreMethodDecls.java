// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class MoreMethodDecls extends ManyMethodDeclarations {

    private ManyMethodDeclarations ManyMethodDeclarations;
    private MethodDeclaration MethodDeclaration;

    public MoreMethodDecls (ManyMethodDeclarations ManyMethodDeclarations, MethodDeclaration MethodDeclaration) {
        this.ManyMethodDeclarations=ManyMethodDeclarations;
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.setParent(this);
        this.MethodDeclaration=MethodDeclaration;
        if(MethodDeclaration!=null) MethodDeclaration.setParent(this);
    }

    public ManyMethodDeclarations getManyMethodDeclarations() {
        return ManyMethodDeclarations;
    }

    public void setManyMethodDeclarations(ManyMethodDeclarations ManyMethodDeclarations) {
        this.ManyMethodDeclarations=ManyMethodDeclarations;
    }

    public MethodDeclaration getMethodDeclaration() {
        return MethodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration MethodDeclaration) {
        this.MethodDeclaration=MethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.accept(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.traverseTopDown(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.traverseBottomUp(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreMethodDecls(\n");

        if(ManyMethodDeclarations!=null)
            buffer.append(ManyMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclaration!=null)
            buffer.append(MethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreMethodDecls]");
        return buffer.toString();
    }
}
