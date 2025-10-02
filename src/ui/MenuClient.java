package ui;

import dao.ClientDAOImpl;
import entity.Client;
import service.ClientService;
import service.InterfaceService.ClientServiceInterface;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuClient{

    private final ClientServiceInterface clientService;
    private final Scanner scanner = new Scanner(System.in);
}
