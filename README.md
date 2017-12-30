# balance-comparison
This is a mini simulation project, to compare two or more data sources coming from different systems, 
Here it compares two account statements and computes the difference and percentage difference 
between the balances for a given transaction type(debit/credit) reporting month.  
key Highlight 
- Exposed the comparison service as a restful-web-service with JAX-RS using Jersey ver1.8
- Uses executor service to partition data based on sources/reporting-period/transaction type 
- performs in memory aggregations and show comparison results
- uses maven xjc plug-in to generate java source objects via xsd
- marshals the source data on server start up and holds it in memory
- Service layer implemented via Spring-3
