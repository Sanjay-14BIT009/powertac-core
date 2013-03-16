/*
* Copyright 2011-2013 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an
* "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
* either express or implied. See the License for the specific language
* governing permissions and limitations under the License.
*/

package org.powertac.common;

import java.util.List;

import org.powertac.common.repo.TimeslotRepo;
import org.powertac.common.spring.SpringApplicationContext;
import org.powertac.common.state.Domain;
//import org.powertac.common.xml.TimeslotConverter;
import com.thoughtworks.xstream.annotations.*;

/**
* A collection of weatherReports giving hourly forecasts for future timeslot.
*
* @author Erik Onarheim and Josh Edeen
*/
@Domain
@XStreamAlias("weather-forecast")
public class WeatherForecast 
{
  @XStreamAsAttribute
  private long id = IdGenerator.createId();
  
  /** the current or reference timeslot from which the weather (forecast) is generated */
  @XStreamAsAttribute
  //@XStreamConverter(TimeslotConverter.class)
  private int currentTimeslot;

  @XStreamImplicit(itemFieldName = "prediction")
  private List<WeatherForecastPrediction> predictions;
  
  public WeatherForecast (int timeslot, List<WeatherForecastPrediction> predictions)
  {
    super();
    this.predictions = predictions;
    this.currentTimeslot = timeslot;
  }
  
  public WeatherForecast (Timeslot timeslot, List<WeatherForecastPrediction> predictions)
  {
    this(timeslot.getSerialNumber(), predictions);
  }

  public List<WeatherForecastPrediction> getPredictions ()
  {
    return predictions;
  }

  public long getId ()
  {
    return id;
  }

  public int getTimeslotIndex()
  {
    return currentTimeslot;
  }
  
  public Timeslot getCurrentTimeslot ()
  {
    return getTimeslotRepo().findBySerialNumber(currentTimeslot);
  }
  
  public Timeslot getTimeslot ()
  {
    return getCurrentTimeslot();
  }
  
  // access to TimeslotRepo
  private static TimeslotRepo timeslotRepo;
  
  private static TimeslotRepo getTimeslotRepo()
  {
    if (null == timeslotRepo) {
      timeslotRepo = (TimeslotRepo) SpringApplicationContext.getBean("timeslotRepo");
    }
    return timeslotRepo;
  }
}
