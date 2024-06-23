// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class HasNamespace1 extends Namespace {

    private Namespace Namespace;
    private HasNamespace HasNamespace;

    public HasNamespace1 (Namespace Namespace, HasNamespace HasNamespace) {
        this.Namespace=Namespace;
        if(Namespace!=null) Namespace.setParent(this);
        this.HasNamespace=HasNamespace;
        if(HasNamespace!=null) HasNamespace.setParent(this);
    }

    public Namespace getNamespace() {
        return Namespace;
    }

    public void setNamespace(Namespace Namespace) {
        this.Namespace=Namespace;
    }

    public HasNamespace getHasNamespace() {
        return HasNamespace;
    }

    public void setHasNamespace(HasNamespace HasNamespace) {
        this.HasNamespace=HasNamespace;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Namespace!=null) Namespace.accept(visitor);
        if(HasNamespace!=null) HasNamespace.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Namespace!=null) Namespace.traverseTopDown(visitor);
        if(HasNamespace!=null) HasNamespace.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Namespace!=null) Namespace.traverseBottomUp(visitor);
        if(HasNamespace!=null) HasNamespace.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("HasNamespace1(\n");

        if(Namespace!=null)
            buffer.append(Namespace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(HasNamespace!=null)
            buffer.append(HasNamespace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [HasNamespace1]");
        return buffer.toString();
    }
}
