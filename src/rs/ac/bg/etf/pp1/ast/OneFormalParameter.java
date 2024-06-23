// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class OneFormalParameter extends FormalParamsList {

    private SingleFormalParam SingleFormalParam;

    public OneFormalParameter (SingleFormalParam SingleFormalParam) {
        this.SingleFormalParam=SingleFormalParam;
        if(SingleFormalParam!=null) SingleFormalParam.setParent(this);
    }

    public SingleFormalParam getSingleFormalParam() {
        return SingleFormalParam;
    }

    public void setSingleFormalParam(SingleFormalParam SingleFormalParam) {
        this.SingleFormalParam=SingleFormalParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleFormalParam!=null) SingleFormalParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleFormalParam!=null) SingleFormalParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleFormalParam!=null) SingleFormalParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneFormalParameter(\n");

        if(SingleFormalParam!=null)
            buffer.append(SingleFormalParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneFormalParameter]");
        return buffer.toString();
    }
}
