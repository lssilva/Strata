/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.rate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableSet;
import com.opengamma.basics.index.IborIndex;
import com.opengamma.basics.index.Index;
import com.opengamma.collect.ArgChecker;
import com.opengamma.collect.Messages;

/**
 * Defines the observation of a rate of interest interpolated from two IBOR-like indices.
 * <p>
 * An interest rate determined from two IBOR-like indices by linear interpolation.
 * Both indices are observed on the same fixing date and they must have the same currency.
 * For example, linear interpolation between 'GBP-LIBOR-1M' and 'GBP-LIBOR-3M'.
 */
@BeanDefinition
public final class IborInterpolatedRateObservation
    implements RateObservation, ImmutableBean, Serializable {

  /**
   * The shorter IBOR-like index.
   * <p>
   * The rate to be paid is based on this index
   * It will be a well known market index such as 'GBP-LIBOR-1M'.
   */
  @PropertyDefinition(validate = "notNull")
  private final IborIndex shortIndex;
  /**
   * The longer IBOR-like index.
   * <p>
   * The rate to be paid is based on this index
   * It will be a well known market index such as 'GBP-LIBOR-3M'.
   */
  @PropertyDefinition(validate = "notNull")
  private final IborIndex longIndex;
  /**
   * The date of the index fixing.
   * <p>
   * This is an adjusted date with any business day applied.
   * Valid business days are defined by {@link IborIndex#getFixingCalendar()}.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate fixingDate;

  //-------------------------------------------------------------------------
  /**
   * Creates an {@code IborInterpolatedRateObservation} from two indices and fixing date.
   * <p>
   * The indices may be passed in any order.
   * 
   * @param index1  the first index
   * @param index2  the second index
   * @param fixingDate  the fixing date
   * @return the interpolated IBOR rate
   */
  public static IborInterpolatedRateObservation of(IborIndex index1, IborIndex index2, LocalDate fixingDate) {
    ArgChecker.notNull(index1, "index1");
    ArgChecker.notNull(index2, "index2");
    ArgChecker.notNull(fixingDate, "fixingDate");
    boolean inOrder = indicesInOrder(index1, index2, fixingDate);
    return IborInterpolatedRateObservation.builder()
        .shortIndex(inOrder ? index1 : index2)
        .longIndex(inOrder ? index2 : index1)
        .fixingDate(fixingDate)
        .build();
  }

  //-------------------------------------------------------------------------
  @ImmutableValidator
  private void validate() {
    if (!shortIndex.getCurrency().equals(longIndex.getCurrency())) {
      throw new IllegalArgumentException("Interpolation requires two indices in the same currency");
    }
    if (shortIndex.equals(longIndex)) {
      throw new IllegalArgumentException("Interpolation requires two different indices");
    }
    if (!indicesInOrder(shortIndex, longIndex, fixingDate)) {
      throw new IllegalArgumentException(
          Messages.format("Interpolation indices passed in wrong order: {} {}", shortIndex, longIndex));
    }
  }

  // checks that the indices are in order
  private static boolean indicesInOrder(IborIndex index1, IborIndex index2, LocalDate fixingDate) {
    return fixingDate.plus(index1.getTenor()).isBefore(fixingDate.plus(index2.getTenor()));
  }

  //-------------------------------------------------------------------------
  @Override
  public void collectIndices(ImmutableSet.Builder<Index> builder) {
    builder.add(shortIndex);
    builder.add(longIndex);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IborInterpolatedRateObservation}.
   * @return the meta-bean, not null
   */
  public static IborInterpolatedRateObservation.Meta meta() {
    return IborInterpolatedRateObservation.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IborInterpolatedRateObservation.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static IborInterpolatedRateObservation.Builder builder() {
    return new IborInterpolatedRateObservation.Builder();
  }

  private IborInterpolatedRateObservation(
      IborIndex shortIndex,
      IborIndex longIndex,
      LocalDate fixingDate) {
    JodaBeanUtils.notNull(shortIndex, "shortIndex");
    JodaBeanUtils.notNull(longIndex, "longIndex");
    JodaBeanUtils.notNull(fixingDate, "fixingDate");
    this.shortIndex = shortIndex;
    this.longIndex = longIndex;
    this.fixingDate = fixingDate;
    validate();
  }

  @Override
  public IborInterpolatedRateObservation.Meta metaBean() {
    return IborInterpolatedRateObservation.Meta.INSTANCE;
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
   * Gets the shorter IBOR-like index.
   * <p>
   * The rate to be paid is based on this index
   * It will be a well known market index such as 'GBP-LIBOR-1M'.
   * @return the value of the property, not null
   */
  public IborIndex getShortIndex() {
    return shortIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the longer IBOR-like index.
   * <p>
   * The rate to be paid is based on this index
   * It will be a well known market index such as 'GBP-LIBOR-3M'.
   * @return the value of the property, not null
   */
  public IborIndex getLongIndex() {
    return longIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the date of the index fixing.
   * <p>
   * This is an adjusted date with any business day applied.
   * Valid business days are defined by {@link IborIndex#getFixingCalendar()}.
   * @return the value of the property, not null
   */
  public LocalDate getFixingDate() {
    return fixingDate;
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
      IborInterpolatedRateObservation other = (IborInterpolatedRateObservation) obj;
      return JodaBeanUtils.equal(getShortIndex(), other.getShortIndex()) &&
          JodaBeanUtils.equal(getLongIndex(), other.getLongIndex()) &&
          JodaBeanUtils.equal(getFixingDate(), other.getFixingDate());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getShortIndex());
    hash = hash * 31 + JodaBeanUtils.hashCode(getLongIndex());
    hash = hash * 31 + JodaBeanUtils.hashCode(getFixingDate());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("IborInterpolatedRateObservation{");
    buf.append("shortIndex").append('=').append(getShortIndex()).append(',').append(' ');
    buf.append("longIndex").append('=').append(getLongIndex()).append(',').append(' ');
    buf.append("fixingDate").append('=').append(JodaBeanUtils.toString(getFixingDate()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IborInterpolatedRateObservation}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code shortIndex} property.
     */
    private final MetaProperty<IborIndex> shortIndex = DirectMetaProperty.ofImmutable(
        this, "shortIndex", IborInterpolatedRateObservation.class, IborIndex.class);
    /**
     * The meta-property for the {@code longIndex} property.
     */
    private final MetaProperty<IborIndex> longIndex = DirectMetaProperty.ofImmutable(
        this, "longIndex", IborInterpolatedRateObservation.class, IborIndex.class);
    /**
     * The meta-property for the {@code fixingDate} property.
     */
    private final MetaProperty<LocalDate> fixingDate = DirectMetaProperty.ofImmutable(
        this, "fixingDate", IborInterpolatedRateObservation.class, LocalDate.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "shortIndex",
        "longIndex",
        "fixingDate");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1545478582:  // shortIndex
          return shortIndex;
        case 107618230:  // longIndex
          return longIndex;
        case 1255202043:  // fixingDate
          return fixingDate;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public IborInterpolatedRateObservation.Builder builder() {
      return new IborInterpolatedRateObservation.Builder();
    }

    @Override
    public Class<? extends IborInterpolatedRateObservation> beanType() {
      return IborInterpolatedRateObservation.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code shortIndex} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IborIndex> shortIndex() {
      return shortIndex;
    }

    /**
     * The meta-property for the {@code longIndex} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IborIndex> longIndex() {
      return longIndex;
    }

    /**
     * The meta-property for the {@code fixingDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> fixingDate() {
      return fixingDate;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1545478582:  // shortIndex
          return ((IborInterpolatedRateObservation) bean).getShortIndex();
        case 107618230:  // longIndex
          return ((IborInterpolatedRateObservation) bean).getLongIndex();
        case 1255202043:  // fixingDate
          return ((IborInterpolatedRateObservation) bean).getFixingDate();
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
   * The bean-builder for {@code IborInterpolatedRateObservation}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<IborInterpolatedRateObservation> {

    private IborIndex shortIndex;
    private IborIndex longIndex;
    private LocalDate fixingDate;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(IborInterpolatedRateObservation beanToCopy) {
      this.shortIndex = beanToCopy.getShortIndex();
      this.longIndex = beanToCopy.getLongIndex();
      this.fixingDate = beanToCopy.getFixingDate();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1545478582:  // shortIndex
          return shortIndex;
        case 107618230:  // longIndex
          return longIndex;
        case 1255202043:  // fixingDate
          return fixingDate;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1545478582:  // shortIndex
          this.shortIndex = (IborIndex) newValue;
          break;
        case 107618230:  // longIndex
          this.longIndex = (IborIndex) newValue;
          break;
        case 1255202043:  // fixingDate
          this.fixingDate = (LocalDate) newValue;
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
    public IborInterpolatedRateObservation build() {
      return new IborInterpolatedRateObservation(
          shortIndex,
          longIndex,
          fixingDate);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code shortIndex} property in the builder.
     * @param shortIndex  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder shortIndex(IborIndex shortIndex) {
      JodaBeanUtils.notNull(shortIndex, "shortIndex");
      this.shortIndex = shortIndex;
      return this;
    }

    /**
     * Sets the {@code longIndex} property in the builder.
     * @param longIndex  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder longIndex(IborIndex longIndex) {
      JodaBeanUtils.notNull(longIndex, "longIndex");
      this.longIndex = longIndex;
      return this;
    }

    /**
     * Sets the {@code fixingDate} property in the builder.
     * @param fixingDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fixingDate(LocalDate fixingDate) {
      JodaBeanUtils.notNull(fixingDate, "fixingDate");
      this.fixingDate = fixingDate;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("IborInterpolatedRateObservation.Builder{");
      buf.append("shortIndex").append('=').append(JodaBeanUtils.toString(shortIndex)).append(',').append(' ');
      buf.append("longIndex").append('=').append(JodaBeanUtils.toString(longIndex)).append(',').append(' ');
      buf.append("fixingDate").append('=').append(JodaBeanUtils.toString(fixingDate));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
