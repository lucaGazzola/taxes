[![Build Status](https://travis-ci.com/lucaGazzola/taxes.svg?branch=master)](https://travis-ci.com/lucaGazzola/taxes.svg?branch=master)
<br />

# Sales Taxes

This project implements a simple tax calculator on a list of items.

## Assumptions

The input is supplied as a `String` that represents a list of items in the following form:

`item quantity` `item name` at `item price`

example input:
```
1 book at 12.49
1 music CD at 14.99
1 chocolate bar at 0.85
```

The output will include a list of the same items with the price updated to include taxes and a summary with the total amount of taxes and the total price of the items in the list, with the form:

`item quantity` `item name`: `item price including taxes`

example output:
```
1 book: 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83
```

Input prices must be positive and have at most 2 numbers representing the fractional part.
Item quantities must be positive.
