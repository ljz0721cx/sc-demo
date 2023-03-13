package com.sc.ynk.pint;

/**
 * @author Janle on 2022/11/18
 */
public class MessageQrCodeVo {
    /**
     * 入库子项ID
     */
    private Long id;

    /**
     * 物料标签id
     */
    private Integer materialLabelId;

    /**
     * 货位id
     */
    private Long goodsAllocationId;

    /**
     * 物料id
     */
    private Long itemId;

    /**
     * 标签表里的入库子项id
     */
    private Long rukuSubitemId;

    /**
     * 项目号
     */
    private String projectNumber;

    /**
     * 数量
     */
    private Integer hadQty;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 规格信息号
     */
    private String originalModel;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 订单号
     */
    private String billNumber;

    /**
     * 版本
     */
    private String version;

    /**
     * 组件号
     */
    private String parentItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaterialLabelId() {
        return materialLabelId;
    }

    public void setMaterialLabelId(Integer materialLabelId) {
        this.materialLabelId = materialLabelId;
    }

    public Long getGoodsAllocationId() {
        return goodsAllocationId;
    }

    public void setGoodsAllocationId(Long goodsAllocationId) {
        this.goodsAllocationId = goodsAllocationId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getRukuSubitemId() {
        return rukuSubitemId;
    }

    public void setRukuSubitemId(Long rukuSubitemId) {
        this.rukuSubitemId = rukuSubitemId;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Integer getHadQty() {
        return hadQty;
    }

    public void setHadQty(Integer hadQty) {
        this.hadQty = hadQty;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOriginalModel() {
        return originalModel;
    }

    public void setOriginalModel(String originalModel) {
        this.originalModel = originalModel;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }
}
