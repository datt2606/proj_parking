import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { CoreConfigService } from "@core/services/config.service";
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexGrid,
  ApexStroke,
  ApexLegend,
  ApexTitleSubtitle,
  ApexTooltip,
  ApexPlotOptions,
  ApexYAxis,
  ApexFill,
  ApexMarkers,
  ApexTheme,
  ApexNonAxisChartSeries,
  ApexResponsive,
  ApexStates,
} from "ng-apexcharts";
import { DashboardService } from "./dashboard.service";

export interface ChartOptions {
  series?: ApexAxisChartSeries;
  chart?: ApexChart;
  xaxis?: ApexXAxis;
  dataLabels?: ApexDataLabels;
  grid?: ApexGrid;
  stroke?: ApexStroke;
  legend?: ApexLegend;
  title?: ApexTitleSubtitle;
  colors?: string[];
  tooltip?: ApexTooltip;
  plotOptions?: ApexPlotOptions;
  yaxis?: ApexYAxis;
  fill?: ApexFill;
  labels?: string[];
  markers: ApexMarkers;
  theme: ApexTheme;
}

export interface ChartOptions2 {
  // Apex-non-axis-chart-series
  series?: ApexNonAxisChartSeries;
  chart?: ApexChart;
  stroke?: ApexStroke;
  tooltip?: ApexTooltip;
  dataLabels?: ApexDataLabels;
  fill?: ApexFill;
  colors?: string[];
  legend?: ApexLegend;
  labels?: any;
  plotOptions?: ApexPlotOptions;
  responsive?: ApexResponsive[];
  markers?: ApexMarkers[];
  xaxis?: ApexXAxis;
  yaxis?: ApexYAxis;
  states?: ApexStates;
}

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  @ViewChild("userWithAgeChartRef") userWithAgeChartRef: any;
  public userWithAgeChart: Partial<ChartOptions>;

  @ViewChild("revenueChartRef") revenueChartRef: any;
  public revenueChart: Partial<ChartOptions>;

  @ViewChild("parkingTotalChartRef") parkingTotalChartRef: any;
  public parkingTotalChart: Partial<ChartOptions2>;

  chartColors = {
    column: {
      series1: "#826af9",
      series2: "#d2b0ff",
      bg: "#f8d3ff",
    },
    success: {
      shade_100: "#7eefc7",
      shade_200: "#06774f",
    },
    donut: {
      series1: "#ffe700",
      series2: "#00d4bd",
      series3: "#826bf8",
      series4: "#2b9bf4",
      series5: "#FFA1A1",
    },
    area: {
      series3: "#a4f8cd",
      series2: "#60f2ca",
      series1: "#2bdac7",
    },
  };

  constructor(
    private _dashboardService: DashboardService,
    private _coreConfigService: CoreConfigService
  ) {
    // Apex Donut Chart
    this.parkingTotalChart = {
      series: [],
      chart: {
        height: 350,
        type: "donut",
      },
      colors: [],
      plotOptions: {
        pie: {
          donut: {
            labels: {
              show: true,
              name: {
                fontSize: "2rem",
                fontFamily: "Montserrat",
              },
              value: {
                fontSize: "1rem",
                fontFamily: "Montserrat",
                formatter: function (val) {
                  return parseInt(val) + "%";
                },
              },
              total: {
                show: true,
                fontSize: "1.5rem",
                label: "Operational",
                formatter: function (w) {
                  return "31%";
                },
              },
            },
          },
        },
      },
      legend: {
        show: true,
        position: "bottom",
      },
      labels: [],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              height: 300,
            },
            legend: {
              position: "bottom",
            },
          },
        },
      ],
    };
  }

  ngOnInit(): void {
    this._dashboardService.getUserWithAge().subscribe((res) => {
      this.userWithAgeChart = {
        series: [
          {
            name: "",
            data: res.data.map((item) => item.count),
          },
        ],
        chart: {
          type: "bar",
          height: 400,
          stacked: true,
          toolbar: {
            show: false,
          },
        },
        grid: {
          xaxis: {
            lines: {
              show: true,
            },
          },
        },
        legend: {
          show: true,
          position: "top",
          horizontalAlign: "left",
        },
        plotOptions: {
          bar: {
            columnWidth: "35%",
            colors: {
              backgroundBarRadius: 10,
            },
          },
        },
        colors: ["#28C76F"],
        dataLabels: {
          enabled: true,
        },
        stroke: {
          show: true,
          width: 2,
          colors: ["transparent"],
        },
        xaxis: {
          categories: res.data.map((item) => item.age),
        },
        yaxis: {
          title: {
            text: "",
          },
        },
        fill: {
          opacity: 1,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + "";
            },
          },
        },
      };
    });

    this._dashboardService.getRevenue().subscribe((res) => {
      this.revenueChart = {
        series: [
          {
            name: "",
            data: Object.values(res.data) as number[],
          },
        ],
        chart: {
          type: "bar",
          height: 400,
          stacked: true,
          toolbar: {
            show: false,
          },
        },
        grid: {
          xaxis: {
            lines: {
              show: true,
            },
          },
        },
        legend: {
          show: true,
          position: "top",
          horizontalAlign: "left",
        },
        plotOptions: {
          bar: {
            columnWidth: "35%",
            colors: {
              backgroundBarRadius: 10,
            },
          },
        },
        colors: ["#28C76F"],
        dataLabels: {
          enabled: true,
        },
        stroke: {
          show: true,
          width: 2,
          colors: ["transparent"],
        },
        xaxis: {
          categories: Object.keys(res.data),
        },
        yaxis: {
          title: {
            text: "",
          },
        },
        fill: {
          opacity: 1,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + "";
            },
          },
        },
      };
    });

    this._dashboardService.getParkingTotal().subscribe((res) => {
      this.parkingTotalChart = {
        series: Object.values(res.data),
        chart: {
          height: 350,
          type: "donut",
        },
        colors: [
          this.chartColors.donut.series2,
          this.chartColors.donut.series5,
        ],
        plotOptions: {
          pie: {
            donut: {
              labels: {
                show: true,
                name: {
                  fontSize: "2rem",
                  fontFamily: "Montserrat",
                },
                value: {
                  fontSize: "1rem",
                  fontFamily: "Montserrat",
                  formatter: function (val) {
                    return parseInt(val) + "%";
                  },
                },
                total: {
                  show: true,
                  fontSize: "1.5rem",
                  label: "Tổng cộng",
                  formatter: function (w: any) {
                    const arr: any[] = w.config.series;
                    return arr.reduce((a, b) => a + b, 0);
                  },
                },
              },
            },
          },
        },
        legend: {
          show: true,
          position: "bottom",
        },
        labels: ["Đang trong bãi", "Đã rời bãi"],
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                height: 300,
              },
              legend: {
                position: "bottom",
              },
            },
          },
        ],
      };
    });
  }

  ngAfterViewInit() {
    // Subscribe to core config changes
    this._coreConfigService.getConfig().subscribe((config) => {
      // If Menu Collapsed Changes
      if (
        config.layout.menu.collapsed === true ||
        config.layout.menu.collapsed === false
      ) {
        setTimeout(() => {
          // Get Dynamic Width for Charts
          this.userWithAgeChart.chart.width =
            this.userWithAgeChartRef?.nativeElement.offsetWidth;
          this.revenueChart.chart.width =
            this.revenueChartRef?.nativeElement.offsetWidth;
          this.parkingTotalChart.chart.width =
            this.parkingTotalChartRef?.nativeElement.offsetWidth;
        }, 900);
      }
    });
  }
}
