package com.sh.model.ssologin;

import lombok.Data;

@Data
public class Ticket {
    private String ticketFindResult;
    private String ssoTicket;
    private String code;
    private String msg;
}