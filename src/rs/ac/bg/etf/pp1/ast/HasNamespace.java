// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class HasNamespace implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private NamespaceName NamespaceName;
    private Declarations Declarations;

    public HasNamespace (NamespaceName NamespaceName, Declarations Declarations) {
        this.NamespaceName=NamespaceName;
        if(NamespaceName!=null) NamespaceName.setParent(this);
        this.Declarations=Declarations;
        if(Declarations!=null) Declarations.setParent(this);
    }

    public NamespaceName getNamespaceName() {
        return NamespaceName;
    }

    public void setNamespaceName(NamespaceName NamespaceName) {
        this.NamespaceName=NamespaceName;
    }

    public Declarations getDeclarations() {
        return Declarations;
    }

    public void setDeclarations(Declarations Declarations) {
        this.Declarations=Declarations;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NamespaceName!=null) NamespaceName.accept(visitor);
        if(Declarations!=null) Declarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NamespaceName!=null) NamespaceName.traverseTopDown(visitor);
        if(Declarations!=null) Declarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NamespaceName!=null) NamespaceName.traverseBottomUp(visitor);
        if(Declarations!=null) Declarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("HasNamespace(\n");

        if(NamespaceName!=null)
            buffer.append(NamespaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Declarations!=null)
            buffer.append(Declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [HasNamespace]");
        return buffer.toString();
    }
}
