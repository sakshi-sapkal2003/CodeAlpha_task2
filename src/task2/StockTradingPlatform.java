package task2;

import java.util.*;

class Stock 
{
    String symbol;
    double price;
    
    public Stock(String symbol, double price) 
    {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio 
{
    private Map<String, Integer> holdings = new HashMap<>();
    private double balance;
    
    public Portfolio(double initialBalance)
    {
        this.balance = initialBalance;
    }

    public double getBalance()
    {
        return balance;
    }

    public void buyStock(String symbol, int quantity, double price)
    {
        double cost = price * quantity;
        if (cost > balance)
        {
            System.out.println("Insufficient balance to buy " + quantity + " shares of " + symbol);
            return;
        }
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        balance -= cost;
        System.out.println("Bought " + quantity + " shares of " + symbol + " at $" + price + " each.");
    }

    public void sellStock(String symbol, int quantity, double price) 
    {
        if (!holdings.containsKey(symbol) || holdings.get(symbol) < quantity)
        {
            System.out.println("Not enough shares to sell.");
            return;
        }
        holdings.put(symbol, holdings.get(symbol) - quantity);
        if (holdings.get(symbol) == 0) {
            holdings.remove(symbol);
        }
        balance += price * quantity;
        System.out.println("Sold " + quantity + " shares of " + symbol + " at $" + price + " each.");
    }

    public void displayPortfolio(Map<String, Stock> marketData) 
    {
        System.out.println("\n===== Portfolio Summary =====");
        System.out.println("Balance: $" + balance);
        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
        } else 
        {
            double totalValue = balance;
            for (String symbol : holdings.keySet())
            {
                int quantity = holdings.get(symbol);
                double currentPrice = marketData.get(symbol).price;
                totalValue += quantity * currentPrice;
                System.out.println(symbol + ": " + quantity + " shares, Value: $" + (quantity * currentPrice));
            }
            System.out.println("Total Portfolio Value: $" + totalValue);
        }
    }
}

public class StockTradingPlatform
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Map<String, Stock> marketData = new HashMap<>();
        marketData.put("AAPL", new Stock("AAPL", 150.0));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.0));
        marketData.put("AMZN", new Stock("AMZN", 3300.0));
        marketData.put("TSLA", new Stock("TSLA", 900.0));

        System.out.print("Enter initial balance: $");
        double initialBalance = scanner.nextDouble();
        Portfolio portfolio = new Portfolio(initialBalance);

        while (true)
        {
            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) 
            {
                case 1:
                    System.out.println("\n===== Market Data =====");
                    for (Stock stock : marketData.values())
                    {
                        System.out.println(stock.symbol + " - $" + stock.price);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    if (!marketData.containsKey(buySymbol)) 
                    {
                        System.out.println("Invalid stock symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    portfolio.buyStock(buySymbol, buyQuantity, marketData.get(buySymbol).price);
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    if (!marketData.containsKey(sellSymbol)) 
                    {
                        System.out.println("Invalid stock symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    portfolio.sellStock(sellSymbol, sellQuantity, marketData.get(sellSymbol).price);
                    break;
                case 4:
                    portfolio.displayPortfolio(marketData);
                    break;
                case 5:
                    System.out.println("Exiting Stock Trading Platform.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

