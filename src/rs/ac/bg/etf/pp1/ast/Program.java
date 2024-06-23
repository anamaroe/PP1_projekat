// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private ProgName ProgName;
    private Namespace Namespace;
    private ConstAndVarDeclarations ConstAndVarDeclarations;
    private ManyMethodDeclarations ManyMethodDeclarations;

    public Program (ProgName ProgName, Namespace Namespace, ConstAndVarDeclarations ConstAndVarDeclarations, ManyMethodDeclarations ManyMethodDeclarations) {
        this.ProgName=ProgName;
        if(ProgName!=null) ProgName.setParent(this);
        this.Namespace=Namespace;
        if(Namespace!=null) Namespace.setParent(this);
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.setParent(this);
        this.ManyMethodDeclarations=ManyMethodDeclarations;
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.setParent(this);
    }

    public ProgName getProgName() {
        return ProgName;
    }

    public void setProgName(ProgName ProgName) {
        this.ProgName=ProgName;
    }

    public Namespace getNamespace() {
        return Namespace;
    }

    public void setNamespace(Namespace Namespace) {
        this.Namespace=Namespace;
    }

    public ConstAndVarDeclarations getConstAndVarDeclarations() {
        return ConstAndVarDeclarations;
    }

    public void setConstAndVarDeclarations(ConstAndVarDeclarations ConstAndVarDeclarations) {
        this.ConstAndVarDeclarations=ConstAndVarDeclarations;
    }

    public ManyMethodDeclarations getManyMethodDeclarations() {
        return ManyMethodDeclarations;
    }

    public void setManyMethodDeclarations(ManyMethodDeclarations ManyMethodDeclarations) {
        this.ManyMethodDeclarations=ManyMethodDeclarations;
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
        if(ProgName!=null) ProgName.accept(visitor);
        if(Namespace!=null) Namespace.accept(visitor);
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.accept(visitor);
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgName!=null) ProgName.traverseTopDown(visitor);
        if(Namespace!=null) Namespace.traverseTopDown(visitor);
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseTopDown(visitor);
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgName!=null) ProgName.traverseBottomUp(visitor);
        if(Namespace!=null) Namespace.traverseBottomUp(visitor);
        if(ConstAndVarDeclarations!=null) ConstAndVarDeclarations.traverseBottomUp(visitor);
        if(ManyMethodDeclarations!=null) ManyMethodDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(ProgName!=null)
            buffer.append(ProgName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Namespace!=null)
            buffer.append(Namespace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAndVarDeclarations!=null)
            buffer.append(ConstAndVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ManyMethodDeclarations!=null)
            buffer.append(ManyMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
