package com.robustastudio.robustivityapp.ViewTask;

import java.util.Date;
import java.util.List;

/**
 * Created by sa2r_ on 4/18/2018.
 */

public interface ViewTaskViewInt {
    void viewTask(String id, String name, String description, String assignee, List<String> members, float estimated_hours, Date due_date, float finished_hours, Date startDate, String projectname);
}
