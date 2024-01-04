package com.sprve.eduorder.entity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_order")
@ApiModel(value="Order对象", description="订单")
public class Order implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "课程id")
    private String courseId;
    @ApiModelProperty(value = "课程名称")
    private String courseTitle;
    @ApiModelProperty(value = "课程封面")
    private String courseCover;
    @ApiModelProperty(value = "讲师id")
    private String teacherId;
    @ApiModelProperty(value = "讲师名称")
    private String teacherName;
    @ApiModelProperty(value = "用户id")
    private String memberId;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "用户手机")
    private String mobile;
    @ApiModelProperty(value = "订单金额（分）")
    private BigDecimal totalFee;
    @ApiModelProperty(value = "支付类型（1：微信 2：支付宝）")
    private Integer payType;
    @ApiModelProperty(value = "订单状态（0：未支付 1：已支付）")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
