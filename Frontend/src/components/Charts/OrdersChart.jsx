
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer
} from "recharts";

import "./Charts.css";

function OrdersChart({ data }) {

  const chartData = data || [
    {
      month: "Jan",
      orders: 180
    },
    {
      month: "Feb",
      orders: 220
    },
    {
      month: "Mar",
      orders: 195
    },
    {
      month: "Apr",
      orders: 280
    },
    {
      month: "May",
      orders: 325
    },
    {
      month: "Jun",
      orders: 370
    }
  ];

  return (
    <div className="chart-card">

      <div className="chart-header">

        <div>
          <h3>Orders Overview</h3>

          <p>
            Monthly order volume
          </p>
        </div>

      </div>


      <div className="chart-container">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <BarChart data={chartData}>

            <CartesianGrid
              strokeDasharray="3 3"
              vertical={false}
            />

            <XAxis
              dataKey="month"
              axisLine={false}
              tickLine={false}
            />

            <YAxis
              axisLine={false}
              tickLine={false}
            />

            <Tooltip />

            <Bar
              dataKey="orders"
              fill="#2563eb"
              radius={[6, 6, 0, 0]}
              barSize={35}
            />

          </BarChart>

        </ResponsiveContainer>

      </div>

    </div>
  );
}

export default OrdersChart;

