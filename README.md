
Project Overview:
This project involves two types of users: hotel manager and User. The hotel manager can create rooms or hotels, while Users can only book rooms.

Performance Improvements:

We address the N+1 problem by optimizing SQL queries.

Pagination is implemented to reduce data consumption, displaying only necessary data.

Data fetching from the database is optimized to minimize load and improve performance.

Features:

Dynamic Pricing: Prices vary based on factors such as booking date, whether it's within a week of the check-in date, holidays, and other criteria. You can easily add additional factors to adjust pricing according to your admin needs.

Inventory Refresh: The inventory is updated daily, including dynamic pricing for the next 60 days. Pricing updates are scheduled to occur automatically.

Price Display: On the frontend, we display the average price for available rooms in hotels when a search is performed.

Authentication & Authorization: We use access and refresh tokens to provide better security for the system.
