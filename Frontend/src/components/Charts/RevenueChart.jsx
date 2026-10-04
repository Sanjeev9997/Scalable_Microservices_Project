
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer
} from "recharts";

import "./Charts.css";

function RevenueChart({ data }) {

  const chartData = data || [
    {
      month: "Jan",
      revenue: 120000
    },
    {
      month: "Feb",
      revenue: 145000
    },
    {
      month: "Mar",
      revenue: 138000
    },
    {
      month: "Apr",
      revenue: 172000
    },
    {
      month: "May",
      revenue: 195000
    },
    {
      month: "Jun",
      revenue: 218000
    }
  ];

  return (
    <div className="chart-card">

      <div className="chart-header">

        <div>
          <h3>Revenue Overview</h3>

          <p>
            Monthly revenue performance
          </p>
        </div>

        <select>
          <option>Last 6 months</option>
          <option>Last 12 months</option>
          <option>This year</option>
        </select>

      </div>


      <div className="chart-container">

        <ResponsiveContainer
          width="100%"
          height="100%"
        >

          <LineChart
            data={chartData}
            margin={{
              top: 10,
              right: 20,
              left: 10,
              bottom: 5
            }}
          >

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
              tickFormatter={(value) =>
                `₹${value / 1000}K`
              }
            />

            <Tooltip
              formatter={(value) =>
                `₹${value.toLocaleString("en-IN")}`
              }
            />

            <Line
              type="monotone"
              dataKey="revenue"
              stroke="#2563eb"
              strokeWidth={3}
              dot={{ r: 4 }}
              activeDot={{ r: 7 }}
            />

          </LineChart>

        </ResponsiveContainer>

      </div>

    </div>
  );
}

export default RevenueChart;

