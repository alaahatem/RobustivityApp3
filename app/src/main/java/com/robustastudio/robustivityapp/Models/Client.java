package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by hp on 28/03/2018.
 */
@Entity
public class Client
{
    public String name;
    public String email;
    public String phone;
    public String Account ;
}
