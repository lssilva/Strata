/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.function.marketdata.mapping;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.market.MarketDataFeed;
import com.opengamma.strata.basics.market.MarketDataId;
import com.opengamma.strata.engine.marketdata.mapping.MarketDataMapping;
import com.opengamma.strata.market.curve.CurveGroupName;
import com.opengamma.strata.market.id.DiscountFactorsId;
import com.opengamma.strata.market.key.DiscountFactorsKey;
import com.opengamma.strata.market.value.DiscountFactors;

/**
 * Market data mapping that accepts a {@link DiscountFactorsKey} and returns
 * a {@link DiscountFactorsId}.
 * <p>
 * The additional information required is the curve group and market data feed
 * that is the source of the curve.
 */
@BeanDefinition
public final class DiscountFactorsMapping
    implements MarketDataMapping<DiscountFactors, DiscountFactorsKey>, ImmutableBean {

  /**
   * The name of the curve group from which discounting curves should be taken.
   */
  @PropertyDefinition(validate = "notNull")
  private final CurveGroupName curveGroupName;
  /**
   * The market data feed which provides quotes used to build the curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final MarketDataFeed marketDataFeed;

  //-------------------------------------------------------------------------
  /**
   * Returns a new mapping based on the specified group and feed.
   * <p>
   * The result will map a {@link DiscountFactorsKey} to a {@link DiscountFactorsId}
   * with the name of the curve group that is the source of the curve.
   *
   * @param curveGroupName  the name of the curve group
   * @param marketDataFeed  the market data feed which provides quotes used to build the curve
   * @return a curve ID with the name of the curve group which is the source of the curve
   */
  public static DiscountFactorsMapping of(CurveGroupName curveGroupName, MarketDataFeed marketDataFeed) {
    return new DiscountFactorsMapping(curveGroupName, marketDataFeed);
  }

  @Override
  public Class<DiscountFactorsKey> getMarketDataKeyType() {
    return DiscountFactorsKey.class;
  }

  @Override
  public MarketDataId<DiscountFactors> getIdForKey(DiscountFactorsKey key) {
    return DiscountFactorsId.of(key.getCurrency(), curveGroupName, marketDataFeed);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DiscountFactorsMapping}.
   * @return the meta-bean, not null
   */
  public static DiscountFactorsMapping.Meta meta() {
    return DiscountFactorsMapping.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DiscountFactorsMapping.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static DiscountFactorsMapping.Builder builder() {
    return new DiscountFactorsMapping.Builder();
  }

  private DiscountFactorsMapping(
      CurveGroupName curveGroupName,
      MarketDataFeed marketDataFeed) {
    JodaBeanUtils.notNull(curveGroupName, "curveGroupName");
    JodaBeanUtils.notNull(marketDataFeed, "marketDataFeed");
    this.curveGroupName = curveGroupName;
    this.marketDataFeed = marketDataFeed;
  }

  @Override
  public DiscountFactorsMapping.Meta metaBean() {
    return DiscountFactorsMapping.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the curve group from which discounting curves should be taken.
   * @return the value of the property, not null
   */
  public CurveGroupName getCurveGroupName() {
    return curveGroupName;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data feed which provides quotes used to build the curve.
   * @return the value of the property, not null
   */
  public MarketDataFeed getMarketDataFeed() {
    return marketDataFeed;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DiscountFactorsMapping other = (DiscountFactorsMapping) obj;
      return JodaBeanUtils.equal(getCurveGroupName(), other.getCurveGroupName()) &&
          JodaBeanUtils.equal(getMarketDataFeed(), other.getMarketDataFeed());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getCurveGroupName());
    hash = hash * 31 + JodaBeanUtils.hashCode(getMarketDataFeed());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("DiscountFactorsMapping{");
    buf.append("curveGroupName").append('=').append(getCurveGroupName()).append(',').append(' ');
    buf.append("marketDataFeed").append('=').append(JodaBeanUtils.toString(getMarketDataFeed()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DiscountFactorsMapping}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code curveGroupName} property.
     */
    private final MetaProperty<CurveGroupName> curveGroupName = DirectMetaProperty.ofImmutable(
        this, "curveGroupName", DiscountFactorsMapping.class, CurveGroupName.class);
    /**
     * The meta-property for the {@code marketDataFeed} property.
     */
    private final MetaProperty<MarketDataFeed> marketDataFeed = DirectMetaProperty.ofImmutable(
        this, "marketDataFeed", DiscountFactorsMapping.class, MarketDataFeed.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "curveGroupName",
        "marketDataFeed");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -382645893:  // curveGroupName
          return curveGroupName;
        case 842621124:  // marketDataFeed
          return marketDataFeed;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public DiscountFactorsMapping.Builder builder() {
      return new DiscountFactorsMapping.Builder();
    }

    @Override
    public Class<? extends DiscountFactorsMapping> beanType() {
      return DiscountFactorsMapping.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code curveGroupName} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CurveGroupName> curveGroupName() {
      return curveGroupName;
    }

    /**
     * The meta-property for the {@code marketDataFeed} property.
     * @return the meta-property, not null
     */
    public MetaProperty<MarketDataFeed> marketDataFeed() {
      return marketDataFeed;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -382645893:  // curveGroupName
          return ((DiscountFactorsMapping) bean).getCurveGroupName();
        case 842621124:  // marketDataFeed
          return ((DiscountFactorsMapping) bean).getMarketDataFeed();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code DiscountFactorsMapping}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<DiscountFactorsMapping> {

    private CurveGroupName curveGroupName;
    private MarketDataFeed marketDataFeed;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(DiscountFactorsMapping beanToCopy) {
      this.curveGroupName = beanToCopy.getCurveGroupName();
      this.marketDataFeed = beanToCopy.getMarketDataFeed();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -382645893:  // curveGroupName
          return curveGroupName;
        case 842621124:  // marketDataFeed
          return marketDataFeed;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -382645893:  // curveGroupName
          this.curveGroupName = (CurveGroupName) newValue;
          break;
        case 842621124:  // marketDataFeed
          this.marketDataFeed = (MarketDataFeed) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public DiscountFactorsMapping build() {
      return new DiscountFactorsMapping(
          curveGroupName,
          marketDataFeed);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the name of the curve group from which discounting curves should be taken.
     * @param curveGroupName  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder curveGroupName(CurveGroupName curveGroupName) {
      JodaBeanUtils.notNull(curveGroupName, "curveGroupName");
      this.curveGroupName = curveGroupName;
      return this;
    }

    /**
     * Sets the market data feed which provides quotes used to build the curve.
     * @param marketDataFeed  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketDataFeed(MarketDataFeed marketDataFeed) {
      JodaBeanUtils.notNull(marketDataFeed, "marketDataFeed");
      this.marketDataFeed = marketDataFeed;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("DiscountFactorsMapping.Builder{");
      buf.append("curveGroupName").append('=').append(JodaBeanUtils.toString(curveGroupName)).append(',').append(' ');
      buf.append("marketDataFeed").append('=').append(JodaBeanUtils.toString(marketDataFeed));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
