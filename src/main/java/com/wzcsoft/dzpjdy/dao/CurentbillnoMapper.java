package com.wzcsoft.dzpjdy.dao;

import com.wzcsoft.dzpjdy.domain.Curentbillno;
import com.wzcsoft.dzpjdy.domain.CurentbillnoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CurentbillnoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    long countByExample(CurentbillnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int deleteByExample(CurentbillnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int insert(Curentbillno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int insertSelective(Curentbillno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    List<Curentbillno> selectByExample(CurentbillnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    Curentbillno selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Curentbillno record, @Param("example") CurentbillnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Curentbillno record, @Param("example") CurentbillnoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Curentbillno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table curentbillno
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Curentbillno record);
}