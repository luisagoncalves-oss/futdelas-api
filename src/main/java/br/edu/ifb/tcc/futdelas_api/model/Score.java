package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Score {
   private Integer current;
   private Integer display;
   private Integer period1;
   private Integer period2;
   private Integer normaltime;

   public Integer getCurrent() {
      return current;
   }
   public void setCurrent(Integer current) {
      this.current = current;
   }
   public Integer getDisplay() {
      return display;
   }
   public void setDisplay(Integer display) {
      this.display = display;
   }
   public Integer getPeriod1() {
      return period1;
   }
   public void setPeriod1(Integer period1) {
      this.period1 = period1;
   }
   public Integer getPeriod2() {
      return period2;
   }
   public void setPeriod2(Integer period2) {
      this.period2 = period2;
   }
   public Integer getNormaltime() {
      return normaltime;
   }
   public void setNormaltime(Integer normaltime) {
      this.normaltime = normaltime;
   } 
}