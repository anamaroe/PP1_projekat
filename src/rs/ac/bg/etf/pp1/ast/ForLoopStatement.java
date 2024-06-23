// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class ForLoopStatement extends Statement {

    private DesignatorStatementsListHead DesignatorStatementsListHead;
    private ForCondFact ForCondFact;
    private DesignatorStatementsListTail DesignatorStatementsListTail;
    private StartOfForLoop StartOfForLoop;
    private Statement Statement;

    public ForLoopStatement (DesignatorStatementsListHead DesignatorStatementsListHead, ForCondFact ForCondFact, DesignatorStatementsListTail DesignatorStatementsListTail, StartOfForLoop StartOfForLoop, Statement Statement) {
        this.DesignatorStatementsListHead=DesignatorStatementsListHead;
        if(DesignatorStatementsListHead!=null) DesignatorStatementsListHead.setParent(this);
        this.ForCondFact=ForCondFact;
        if(ForCondFact!=null) ForCondFact.setParent(this);
        this.DesignatorStatementsListTail=DesignatorStatementsListTail;
        if(DesignatorStatementsListTail!=null) DesignatorStatementsListTail.setParent(this);
        this.StartOfForLoop=StartOfForLoop;
        if(StartOfForLoop!=null) StartOfForLoop.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public DesignatorStatementsListHead getDesignatorStatementsListHead() {
        return DesignatorStatementsListHead;
    }

    public void setDesignatorStatementsListHead(DesignatorStatementsListHead DesignatorStatementsListHead) {
        this.DesignatorStatementsListHead=DesignatorStatementsListHead;
    }

    public ForCondFact getForCondFact() {
        return ForCondFact;
    }

    public void setForCondFact(ForCondFact ForCondFact) {
        this.ForCondFact=ForCondFact;
    }

    public DesignatorStatementsListTail getDesignatorStatementsListTail() {
        return DesignatorStatementsListTail;
    }

    public void setDesignatorStatementsListTail(DesignatorStatementsListTail DesignatorStatementsListTail) {
        this.DesignatorStatementsListTail=DesignatorStatementsListTail;
    }

    public StartOfForLoop getStartOfForLoop() {
        return StartOfForLoop;
    }

    public void setStartOfForLoop(StartOfForLoop StartOfForLoop) {
        this.StartOfForLoop=StartOfForLoop;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementsListHead!=null) DesignatorStatementsListHead.accept(visitor);
        if(ForCondFact!=null) ForCondFact.accept(visitor);
        if(DesignatorStatementsListTail!=null) DesignatorStatementsListTail.accept(visitor);
        if(StartOfForLoop!=null) StartOfForLoop.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementsListHead!=null) DesignatorStatementsListHead.traverseTopDown(visitor);
        if(ForCondFact!=null) ForCondFact.traverseTopDown(visitor);
        if(DesignatorStatementsListTail!=null) DesignatorStatementsListTail.traverseTopDown(visitor);
        if(StartOfForLoop!=null) StartOfForLoop.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementsListHead!=null) DesignatorStatementsListHead.traverseBottomUp(visitor);
        if(ForCondFact!=null) ForCondFact.traverseBottomUp(visitor);
        if(DesignatorStatementsListTail!=null) DesignatorStatementsListTail.traverseBottomUp(visitor);
        if(StartOfForLoop!=null) StartOfForLoop.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForLoopStatement(\n");

        if(DesignatorStatementsListHead!=null)
            buffer.append(DesignatorStatementsListHead.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondFact!=null)
            buffer.append(ForCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementsListTail!=null)
            buffer.append(DesignatorStatementsListTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StartOfForLoop!=null)
            buffer.append(StartOfForLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForLoopStatement]");
        return buffer.toString();
    }
}
