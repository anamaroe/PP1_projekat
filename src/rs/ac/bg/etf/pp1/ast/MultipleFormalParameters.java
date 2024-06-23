// generated with ast extension for cup
// version 0.8
// 17/0/2024 2:34:17


package rs.ac.bg.etf.pp1.ast;

public class MultipleFormalParameters extends FormalParamsList {

    private SingleFormalParam SingleFormalParam;
    private FormalParamsList FormalParamsList;

    public MultipleFormalParameters (SingleFormalParam SingleFormalParam, FormalParamsList FormalParamsList) {
        this.SingleFormalParam=SingleFormalParam;
        if(SingleFormalParam!=null) SingleFormalParam.setParent(this);
        this.FormalParamsList=FormalParamsList;
        if(FormalParamsList!=null) FormalParamsList.setParent(this);
    }

    public SingleFormalParam getSingleFormalParam() {
        return SingleFormalParam;
    }

    public void setSingleFormalParam(SingleFormalParam SingleFormalParam) {
        this.SingleFormalParam=SingleFormalParam;
    }

    public FormalParamsList getFormalParamsList() {
        return FormalParamsList;
    }

    public void setFormalParamsList(FormalParamsList FormalParamsList) {
        this.FormalParamsList=FormalParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleFormalParam!=null) SingleFormalParam.accept(visitor);
        if(FormalParamsList!=null) FormalParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleFormalParam!=null) SingleFormalParam.traverseTopDown(visitor);
        if(FormalParamsList!=null) FormalParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleFormalParam!=null) SingleFormalParam.traverseBottomUp(visitor);
        if(FormalParamsList!=null) FormalParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleFormalParameters(\n");

        if(SingleFormalParam!=null)
            buffer.append(SingleFormalParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParamsList!=null)
            buffer.append(FormalParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleFormalParameters]");
        return buffer.toString();
    }
}
