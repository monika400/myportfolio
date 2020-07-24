// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.google.sps.TimeRange.ORDER_BY_START;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    //throw new UnsupportedOperationException("TODO: Implement this method.");
    //SO first we can see that how many attendee are joining other meetings ,in each meeting 
    //we check the commen attendee and mark that interval as not avilable
    //once we get the intervals we have to combine the intervals on the basis of overlaps and 
    //have to check any space is left for meetting or not.

    //step1: find the intervals that are not avilable via iterating over all meeting
    ArrayList<TimeRange> rangenotavilable = findrangenotavilable(events,request);

    //check what are possible intervals 
    ArrayList<TimeRange>meetingpossible = findrangeformeeting(rangenotavilable,request);
    return meetingpossible;
  }
  private ArrayList<TimeRange> findrangeformeeting(ArrayList<TimeRange>range,MeetingRequest request){
    range.sort(ORDER_BY_START);
    ArrayList<TimeRange> meetingpossible = new ArrayList<>();
    int end = 0;

    for (TimeRange thisrange : range) {
      //if range is contained in previous range then do nothing 
      if (thisrange.end() <= end) {
        continue;
      }
      //if range contained in the previous range note it's end time
      if (thisrange.start() < end) {
        end = thisrange.end();
        continue;
      }
      //check for range avilable or not
      if (request.getDuration() <= thisrange.start() - end) {
        meetingpossible.add(TimeRange.fromStartEnd(end, thisrange.start(), false));
      }
      end = thisrange.end();
    }

    if (TimeRange.WHOLE_DAY.end()- end >= request.getDuration()) {
      meetingpossible.add(TimeRange.fromStartEnd(end, TimeRange.WHOLE_DAY.end(), false));
    }

    return meetingpossible;
  }
  
  private ArrayList<TimeRange> findrangenotavilable(Collection<Event> event, MeetingRequest meeting){
    
    ArrayList<TimeRange> rangesnotavilable = new ArrayList<>();
    Collection<String> attendees = meeting.getAttendees();

    for (Event thisevent : event) {
    
      Collection<String> thiseventattendees = thisevent.getAttendees();
    
      boolean RangeAvilable = checkrange(attendees,thiseventattendees);
      
      if (!RangeAvilable) {
        rangesnotavilable.add(thisevent.getWhen());
      }

    } 
    return rangesnotavilable;
  }
  private boolean checkrange(Collection<String>attendees,Collection<String>thiseventattendees){
      for (String attendee : attendees) {
        if (thiseventattendees.contains(attendee)) {
          return false;
        }
      }
      return true;
  }
}
