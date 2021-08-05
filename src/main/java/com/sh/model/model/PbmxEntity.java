package com.sh.model.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PbmxEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long id;// 自增主键
    @FieldDescInject("来源系统")
    public String lyxt;// 来源系统
    @FieldDescInject("保单号")
    public String bdh;// 保单号
    @FieldDescInject("报案号")
    public String bah;// 报案号
    @FieldDescInject("领款人姓名")
    public String lkrxm;// 领款人姓名
    @FieldDescInject("领款人银行开户行")
    public String lkrkhh;// 领款人银行开户行
    @FieldDescInject("领款人银行卡号")
    public String lkryhkh;// 领款人银行卡号
    @FieldDescInject("支付方式(公到私)")
    public String zhifu;// 赔付方式
    @FieldDescInject("雇员姓名")
    public String gyxm;// 雇员姓名
    @FieldDescInject("雇员年龄")
    public String gynl;// 雇员年龄
    @FieldDescInject("雇员身份证号码")
    public String gysfzhm;// 雇员身份证号码
    @FieldDescInject("雇员手机号")
    public String gysjh;// 雇员手机号
    @FieldDescInject("支付方式")
    public String zffs;// 支付方式
    @FieldDescInject("雇员银行开户行")
    public String gyyhkkhh;// 雇员银行开户行 gyyhkkhh
    @FieldDescInject("雇员银行卡号")
    public String gyyhkh;// 雇员银行卡号
    @FieldDescInject("投保人")
    public String tbr;// 投保人
    @FieldDescInject("被保险人")
    public String bbxr;// 被保险人
    @FieldDescInject("行业风险分类")
    public String hyfxfl;// 行业风险分类 hyfxfl
    @FieldDescInject("总出险人次")
    public Integer zcxrc;// 总出险人次
    @FieldDescInject("总投保人次")
    public Integer ztbrc;// 总投保人次
    @FieldDescInject("总理赔金额")
    public String zlpje;// 总理赔金额
    @FieldDescInject("总保费")
    public String zbf;// 总保费
    @FieldDescInject("伤残等级")
    public String scdj;// 伤残等级
    @FieldDescInject("伤残赔偿金")
    public String scpcj;// 伤残赔偿金 scpcj
    @FieldDescInject("医疗费")
    public String ylf;// 医疗费 
    @FieldDescInject("其他费")
    public String qtf;// 其他费 qtf
    @FieldDescInject("营养费")
    public String yyf;// 营养费
    @FieldDescInject("误工费")
    public String wgf;// 误工费
    @FieldDescInject("住院补助")
    public String zybz;// 住院补助
    @FieldDescInject("其他费用")
    public String qtf2;// 其他费2
    @FieldDescInject("赔付金额")
    public String pfje;// 赔付金额
    @FieldDescInject("起保时间")
    public String qbsj;// 起保时间
    @FieldDescInject("出险时间")
    public String cxsj;// 出险时间
    @FieldDescInject("出险地点")
    public String cxdd;// 出险地点
    @FieldDescInject("备注")
    public String bz;// 备注
    public Integer batchId;// 导入的文件id,批次id
    public String jgts;//起保与出险间隔天数
    public String rczb;//出险与投保人次占比
    public String jezb;//保费与理赔金额占比
    public String fyxmszb;//费用项目数占比
    public String scjzz;//伤残金占比
    public String lkrxz;//领款人性质
    public String yhkxz;//银行卡性质
    public String sfkyJgts;//是否可疑
    public String extrp;//是否现金支付
    public String sfkyRczb;//
    public String sfkyJezb;//
    public String sfkyFyxmszb;//
    public String sfkyYlf;//
    public String sfkyScjzz;//
    public String sfkyLkrxz;//
    public String sfkyYhkxz;//
    public String zxfs;//置信度
    public String zql;//准确率
    public String ljpdd;//逻辑判断点
}
