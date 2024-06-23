// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class DesignArrList extends DesignatorArrayList {

    private DesignatorArrayList DesignatorArrayList;
    private HasDesignator HasDesignator;

    public DesignArrList (DesignatorArrayList DesignatorArrayList, HasDesignator HasDesignator) {
        this.DesignatorArrayList=DesignatorArrayList;
        if(DesignatorArrayList!=null) DesignatorArrayList.setParent(this);
        this.HasDesignator=HasDesignator;
        if(HasDesignator!=null) HasDesignator.setParent(this);
    }

    public DesignatorArrayList getDesignatorArrayList() {
        return DesignatorArrayList;
    }

    public void setDesignatorArrayList(DesignatorArrayList DesignatorArrayList) {
        this.DesignatorArrayList=DesignatorArrayList;
    }

    public HasDesignator getHasDesignator() {
        return HasDesignator;
    }

    public void setHasDesignator(HasDesignator HasDesignator) {
        this.HasDesignator=HasDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayList!=null) DesignatorArrayList.accept(visitor);
        if(HasDesignator!=null) HasDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayList!=null) DesignatorArrayList.traverseTopDown(visitor);
        if(HasDesignator!=null) HasDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayList!=null) DesignatorArrayList.traverseBottomUp(visitor);
        if(HasDesignator!=null) HasDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignArrList(\n");

        if(DesignatorArrayList!=null)
            buffer.append(DesignatorArrayList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(HasDesignator!=null)
            buffer.append(HasDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignArrList]");
        return buffer.toString();
    }
}
