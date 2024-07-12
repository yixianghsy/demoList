package com.hsy.tacticsAddTemplate;
/**
 * 模板类
 */
public abstract class CodeProcessAbstract implements ICodeService {
    public  String   getCreateCode(String codeType){
        return null;
    }
    private String getPreCode(String codeType){
        // 假装从库里取出对应类型的code
        System.out.println("从库里取出码值：20230504001或1|"+codeType);
        return "20230504001";
    }
    private void updateNewCode(String newCode,String codeType){
        // 假装将新的编码入库
        System.out.println("将"+codeType+"更新code码为："+newCode);

    }
    protected abstract String generateCode(String preCode);

    @Override
    public String getCode(String codeType) {
        // 获取前一个code码
        String preCode=getPreCode(codeType);
        // 根据旧编号生成新的编号
        String newCode=generateCode(preCode);
        // 将新的编号更新到库里
        updateNewCode(newCode,codeType);
        return newCode;
    }
}
