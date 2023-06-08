package com.example.frontdesk.BindingClasses;

import lombok.Data;

@Data
public class DashBoardResponse {
    private Integer totalEnquiries;
    private Integer enrolled;
    private Integer lostCnt;

}
