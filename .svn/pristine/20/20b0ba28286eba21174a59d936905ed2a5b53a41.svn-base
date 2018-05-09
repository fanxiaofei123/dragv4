//jQuery(document).ready(function() {
//    // ECHARTS
//    require.config({
//        paths: {
//            echarts: '../../assets/global/plugins/echarts/'
//        }
//    });
//
//    // DEMOS
//    require(
//        [
//            'echarts',
//            'echartsart/bar',
//            'echartsartord',
//            'echartsart/eventRiver',
//            'echartsart/force',
//            'echartsart/funnel',
//            'echartsart/gauge',
//            'echartsart/heatmap',
//            'echartsart/k',
//            'echartsartne',
//            'echartsart/map',
//            'echartsart/pie',
//            'echartsart/radar',
//            'echartsart/scatter',
//            'echartsart/tree',
//            'echartsart/treemap',
//            'echartsartenn',
//            'echartsart/wordCloud'
//        ],
//        function(ec) {
//            //--- BAR ---
//            var myChart = ec.init(document.getElementById('echarts_bar'));
//            myChart.setOption({
//                tooltip: {
//                    trigger: 'axis'
//                },
//                legend: {
//                    data: ['Cost', 'Expenses']
//                },
//                toolbox: {
//                    show: true,
//                    feature: {
//                        mark: {
//                            show: true
//                        },
//                        dataView: {
//                            show: true,
//                            readOnly: false
//                        },
//                        magicType: {
//                            show: true,
//                            type: ['line', 'bar']
//                        },
//                        restore: {
//                            show: true
//                        },
//                        saveAsImage: {
//                            show: true
//                        }
//                    }
//                },
//                calculable: true,
//                xAxis: [{
//                    type: 'category',
//                    data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
//                }],
//                yAxis: [{
//                    type: 'value',
//                    splitArea: {
//                        show: true
//                    }
//                }],
//                series: [{
//                    name: 'Cost',
//                    type: 'bar',
//                    data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
//                }, {
//                    name: 'Expenses',
//                    type: 'bar',
//                    data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
//                }]
//            });
//
//            // --- LINE ---
//            var myChart2 = ec.init(document.getElementById('echarts_line'));
//            myChart2.setOption({
//                title: {
//                    text: 'Weekly Weather',
//                    subtext: 'Lorem ipsum'
//                },
//                tooltip: {
//                    trigger: 'axis'
//                },
//                legend: {
//                    data: ['High', 'Low']
//                },
//                toolbox: {
//                    show: true,
//                    feature: {
//                        mark: {
//                            show: true
//                        },
//                        dataView: {
//                            show: true,
//                            readOnly: false
//                        },
//                        magicType: {
//                            show: true,
//                            type: ['line', 'bar']
//                        },
//                        restore: {
//                            show: true
//                        },
//                        saveAsImage: {
//                            show: true
//                        }
//                    }
//                },
//                calculable: true,
//                xAxis: [{
//                    type: 'category',
//                    boundaryGap: false,
//                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
//                }],
//                yAxis: [{
//                    type: 'value',
//                    axisLabel: {
//                        formatter: '{value} °C'
//                    }
//                }],
//                series: [{
//                    name: 'High',
//                    type: 'line',
//                    data: [11, 11, 15, 13, 12, 13, 10],
//                    markPoint: {
//                        data: [{
//                            type: 'max',
//                            name: 'Max'
//                        }, {
//                            type: 'min',
//                            name: 'Min'
//                        }]
//                    },
//                    markLine: {
//                        data: [{
//                            type: 'average',
//                            name: 'Mean'
//                        }]
//                    }
//                }, {
//                    name: 'Low',
//                    type: 'line',
//                    data: [1, -2, 2, 5, 3, 2, 0],
//                    markPoint: {
//                        data: [{
//                            name: 'Lowest',
//                            value: -2,
//                            xAxis: 1,
//                            yAxis: -1.5
//                        }]
//                    },
//                    markLine: {
//                        data: [{
//                            type: 'average',
//                            name: 'Mean'
//                        }]
//                    }
//                }]
//            });
//
//            // -- SCATTER --
//            var myChart3 = ec.init(document.getElementById('echartsMain'));
//            myChart3.setOption({
//                tooltip: {
//                    trigger: 'item'
//                },
//                toolbox: {
//                    show: true,
//                    feature: {
//                        mark: {
//                            show: true
//                        },
//                        dataZoom: {
//                            show: true
//                        },
//                        dataView: {
//                            show: true,
//                            readOnly: false
//                        },
//                        restore: {
//                            show: true
//                        },
//                        saveAsImage: {
//                            show: true
//                        }
//                    }
//                },
//                dataRange: {
//                    min: 0,
//                    max: 100,
//                    y: 'center',
//                    text: ['High', 'Low'],
//                    color: ['lightgreen', 'yellow'],
//                    calculable: true
//                },
//                xAxis: [{
//                    type: 'value',
//                    scale: true
//                }],
//                yAxis: [{
//                    type: 'value',
//                    position: 'right',
//                    scale: true
//                }],
//                animation: false,
//                series: [{
//                    name: 'scatter1',
//                    type: 'scatter',
//                    symbolSize: 5,
//                    data: (function() {
//                        var d = [];
//                        var len = 500;
//                        var value;
//                        while (len--) {
//                            value = (Math.random() * 100).toFixed(2) - 0;
//                            d.push([
//                                (Math.random() * value + value).toFixed(2) - 0, (Math.random() * value).toFixed(2) - 0,
//                                value
//                            ]);
//                        }
//                        return d;
//                    })()
//                }]
//            });
//
//            // -- CANDLESTICK --
//            var myChart4 = ec.init(document.getElementById('echarts_candle'));
//            myChart4.setOption({
//                tooltip: {
//                    trigger: 'axis',
//                    formatter: function(params) {
//                        var res = params[0].seriesName + ' ' + params[0].name;
//                        res += '<br/>  Opening : ' + params[0].value[0] + '  Highest : ' + params[0].value[3];
//                        res += '<br/>  Closing : ' + params[0].value[1] + '  Lowest : ' + params[0].value[2];
//                        return res;
//                    }
//                },
//                legend: {
//                    data: ['Composite Index']
//                },
//                toolbox: {
//                    show: true,
//                    feature: {
//                        mark: {
//                            show: true
//                        },
//                        dataZoom: {
//                            show: true
//                        },
//                        dataView: {
//                            show: true,
//                            readOnly: false
//                        },
//                        restore: {
//                            show: true
//                        },
//                        saveAsImage: {
//                            show: true
//                        }
//                    }
//                },
//                dataZoom: {
//                    show: true,
//                    realtime: true,
//                    start: 0,
//                    end: 50
//                },
//                xAxis: [{
//                    type: 'category',
//                    boundaryGap: true,
//                    axisTick: {
//                        onGap: false
//                    },
//                    splitLine: {
//                        show: false
//                    },
//                    data: [
//                        "2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30",
//                        "2013/1/31", "2013/2/1", "2013/2/4", "2013/2/5", "2013/2/6",
//                        "2013/2/7", "2013/2/8", "2013/2/18", "2013/2/19", "2013/2/20",
//                        "2013/2/21", "2013/2/22", "2013/2/25", "2013/2/26", "2013/2/27",
//                        "2013/2/28", "2013/3/1", "2013/3/4", "2013/3/5", "2013/3/6",
//                        "2013/3/7", "2013/3/8", "2013/3/11", "2013/3/12", "2013/3/13",
//                        "2013/3/14", "2013/3/15", "2013/3/18", "2013/3/19", "2013/3/20",
//                        "2013/3/21", "2013/3/22", "2013/3/25", "2013/3/26", "2013/3/27",
//                        "2013/3/28", "2013/3/29", "2013/4/1", "2013/4/2", "2013/4/3",
//                        "2013/4/8", "2013/4/9", "2013/4/10", "2013/4/11", "2013/4/12",
//                        "2013/4/15", "2013/4/16", "2013/4/17", "2013/4/18", "2013/4/19",
//                        "2013/4/22", "2013/4/23", "2013/4/24", "2013/4/25", "2013/4/26",
//                        "2013/5/2", "2013/5/3", "2013/5/6", "2013/5/7", "2013/5/8",
//                        "2013/5/9", "2013/5/10", "2013/5/13", "2013/5/14", "2013/5/15",
//                        "2013/5/16", "2013/5/17", "2013/5/20", "2013/5/21", "2013/5/22",
//                        "2013/5/23", "2013/5/24", "2013/5/27", "2013/5/28", "2013/5/29",
//                        "2013/5/30", "2013/5/31", "2013/6/3", "2013/6/4", "2013/6/5",
//                        "2013/6/6", "2013/6/7", "2013/6/13"
//                    ]
//                }],
//                yAxis: [{
//                    type: 'value',
//                    scale: true,
//                    boundaryGap: [0.01, 0.01]
//                }],
//                series: [{
//                    name: 'Composite Index',
//                    type: 'k',
//                    barMaxWidth: 20,
//                    itemStyle: {
//                        normal: {
//                            color: 'red', // Bar Colors
//                            color0: 'lightgreen',
//                            lineStyle: {
//                                width: 2,
//                                color: 'orange',
//                                color0: 'green'
//                            }
//                        },
//                        emphasis: {
//                            color: 'black',
//                            color0: 'white'
//                        }
//                    },
//                    data: [ // Opening, Closing, Min, Max
//                        {
//                            value: [2320.26, 2302.6, 2287.3, 2362.94],
//                            itemStyle: {
//                                normal: {
//                                    color0: 'blue', // Opening Fill color
//                                    lineStyle: {
//                                        width: 3,
//                                        color0: 'blue' // Opening Border color
//                                    }
//                                },
//                                emphasis: {
//                                    color0: 'blue' // Opening Fill color
//                                }
//                            }
//                        },
//                        [2300, 2291.3, 2288.26, 2308.38],
//                        [2295.35, 2346.5, 2295.35, 2346.92],
//                        [2347.22, 2358.98, 2337.35, 2363.8],
//                        [2360.75, 2382.48, 2347.89, 2383.76],
//                        [2383.43, 2385.42, 2371.23, 2391.82],
//                        [2377.41, 2419.02, 2369.57, 2421.15],
//                        [2425.92, 2428.15, 2417.58, 2440.38],
//                        [2411, 2433.13, 2403.3, 2437.42],
//                        [2432.68, 2434.48, 2427.7, 2441.73],
//                        [2430.69, 2418.53, 2394.22, 2433.89],
//                        [2416.62, 2432.4, 2414.4, 2443.03],
//                        [2441.91, 2421.56, 2415.43, 2444.8],
//                        [2420.26, 2382.91, 2373.53, 2427.07],
//                        [2383.49, 2397.18, 2370.61, 2397.94],
//                        [2378.82, 2325.95, 2309.17, 2378.82],
//                        [2322.94, 2314.16, 2308.76, 2330.88],
//                        [2320.62, 2325.82, 2315.01, 2338.78],
//                        [2313.74, 2293.34, 2289.89, 2340.71],
//                        [2297.77, 2313.22, 2292.03, 2324.63],
//                        [2322.32, 2365.59, 2308.92, 2366.16],
//                        [2364.54, 2359.51, 2330.86, 2369.65],
//                        [2332.08, 2273.4, 2259.25, 2333.54],
//                        [2274.81, 2326.31, 2270.1, 2328.14],
//                        [2333.61, 2347.18, 2321.6, 2351.44],
//                        [2340.44, 2324.29, 2304.27, 2352.02],
//                        [2326.42, 2318.61, 2314.59, 2333.67],
//                        [2314.68, 2310.59, 2296.58, 2320.96],
//                        [2309.16, 2286.6, 2264.83, 2333.29],
//                        [2282.17, 2263.97, 2253.25, 2286.33],
//                        [2255.77, 2270.28, 2253.31, 2276.22],
//                        [2269.31, 2278.4, 2250, 2312.08],
//                        [2267.29, 2240.02, 2239.21, 2276.05],
//                        [2244.26, 2257.43, 2232.02, 2261.31],
//                        [2257.74, 2317.37, 2257.42, 2317.86],
//                        [2318.21, 2324.24, 2311.6, 2330.81],
//                        [2321.4, 2328.28, 2314.97, 2332],
//                        [2334.74, 2326.72, 2319.91, 2344.89],
//                        [2318.58, 2297.67, 2281.12, 2319.99],
//                        [2299.38, 2301.26, 2289, 2323.48],
//                        [2273.55, 2236.3, 2232.91, 2273.55],
//                        [2238.49, 2236.62, 2228.81, 2246.87],
//                        [2229.46, 2234.4, 2227.31, 2243.95],
//                        [2234.9, 2227.74, 2220.44, 2253.42],
//                        [2232.69, 2225.29, 2217.25, 2241.34],
//                        [2196.24, 2211.59, 2180.67, 2212.59],
//                        [2215.47, 2225.77, 2215.47, 2234.73],
//                        [2224.93, 2226.13, 2212.56, 2233.04],
//                        [2236.98, 2219.55, 2217.26, 2242.48],
//                        [2218.09, 2206.78, 2204.44, 2226.26],
//                        [2199.91, 2181.94, 2177.39, 2204.99],
//                        [2169.63, 2194.85, 2165.78, 2196.43],
//                        [2195.03, 2193.8, 2178.47, 2197.51],
//                        [2181.82, 2197.6, 2175.44, 2206.03],
//                        [2201.12, 2244.64, 2200.58, 2250.11],
//                        [2236.4, 2242.17, 2232.26, 2245.12],
//                        [2242.62, 2184.54, 2182.81, 2242.62],
//                        [2187.35, 2218.32, 2184.11, 2226.12],
//                        [2213.19, 2199.31, 2191.85, 2224.63],
//                        [2203.89, 2177.91, 2173.86, 2210.58],
//                        [2170.78, 2174.12, 2161.14, 2179.65],
//                        [2179.05, 2205.5, 2179.05, 2222.81],
//                        [2212.5, 2231.17, 2212.5, 2236.07],
//                        [2227.86, 2235.57, 2219.44, 2240.26],
//                        [2242.39, 2246.3, 2235.42, 2255.21],
//                        [2246.96, 2232.97, 2221.38, 2247.86],
//                        [2228.82, 2246.83, 2225.81, 2247.67],
//                        [2247.68, 2241.92, 2231.36, 2250.85],
//                        [2238.9, 2217.01, 2205.87, 2239.93],
//                        [2217.09, 2224.8, 2213.58, 2225.19],
//                        [2221.34, 2251.81, 2210.77, 2252.87],
//                        [2249.81, 2282.87, 2248.41, 2288.09],
//                        [2286.33, 2299.99, 2281.9, 2309.39],
//                        [2297.11, 2305.11, 2290.12, 2305.3],
//                        [2303.75, 2302.4, 2292.43, 2314.18],
//                        [2293.81, 2275.67, 2274.1, 2304.95],
//                        [2281.45, 2288.53, 2270.25, 2292.59],
//                        [2286.66, 2293.08, 2283.94, 2301.7],
//                        [2293.4, 2321.32, 2281.47, 2322.1],
//                        [2323.54, 2324.02, 2321.17, 2334.33],
//                        [2316.25, 2317.75, 2310.49, 2325.72],
//                        [2320.74, 2300.59, 2299.37, 2325.53],
//                        [2300.21, 2299.25, 2294.11, 2313.43],
//                        [2297.1, 2272.42, 2264.76, 2297.1],
//                        [2270.71, 2270.93, 2260.87, 2276.86],
//                        [2264.43, 2242.11, 2240.07, 2266.69],
//                        [2242.26, 2210.9, 2205.07, 2250.63],
//                        [2190.1, 2148.35, 2126.22, 2190.1]
//                    ],
//                    markPoint: {
//                        symbol: 'star',
//                        //symbolSize:20,
//                        itemStyle: {
//                            normal: {
//                                label: {
//                                    position: 'top'
//                                }
//                            }
//                        },
//                        data: [{
//                            name: 'Highest',
//                            value: 2444.8,
//                            xAxis: '2013/2/18',
//                            yAxis: 2466
//                        }]
//                    }
//                }]
//            });
//
//            // -- PIE --
//            var myChart5 = ec.init(document.getElementById('echarts_pie'));
//            myChart5.setOption({
//                tooltip: {
//                    show: true,
//                    formatter: "{a} <br/>{b} : {c} ({d}%)"
//                },
//                legend: {
//                    orient: 'vertical',
//                    x: 'left',
//                    data: ['All', 'Marketing', 'Search', 'EDM', 'Partnership', 'Video', 'Social', 'Google', 'Bing', 'Others']
//                },
//                toolbox: {
//                    show: true,
//                    feature: {
//                        mark: {
//                            show: true
//                        },
//                        dataView: {
//                            show: true,
//                            readOnly: false
//                        },
//                        restore: {
//                            show: true
//                        },
//                        saveAsImage: {
//                            show: true
//                        }
//                    }
//                },
//                calculable: true,
//                series: [{
//                    name: 'Source',
//                    type: 'pie',
//                    center: ['35%', 200],
//                    radius: 80,
//                    itemStyle: {
//                        normal: {
//                            label: {
//                                position: 'inner',
//                                formatter: function(params) {
//                                    return (params.percent - 0).toFixed(0) + '%'
//                                }
//                            },
//                            labelLine: {
//                                show: false
//                            }
//                        },
//                        emphasis: {
//                            label: {
//                                show: true,
//                                formatter: "{b}\n{d}%"
//                            }
//                        }
//
//                    },
//                    data: [{
//                        value: 335,
//                        name: 'All'
//                    }, {
//                        value: 679,
//                        name: 'Marketing'
//                    }, {
//                        value: 1548,
//                        name: 'Search'
//                    }]
//                }, {
//                    name: 'Source',
//                    type: 'pie',
//                    center: ['35%', 200],
//                    radius: [110, 140],
//                    data: [{
//                        value: 335,
//                        name: 'All'
//                    }, {
//                        value: 310,
//                        name: 'EDM'
//                    }, {
//                        value: 234,
//                        name: 'Partnership'
//                    }, {
//                        value: 135,
//                        name: 'Video'
//                    }, {
//                        value: 1048,
//                        name: 'Social',
//                        itemStyle: {
//                            normal: {
//                                color: (function() {
//                                    var zrColor = require('zrender/tool/color');
//                                    return zrColor.getRadialGradient(
//                                        300, 200, 110, 300, 200, 140, [
//                                            [0, 'rgba(255,255,0,1)'],
//                                            [1, 'rgba(30,144,250,1)']
//                                        ]
//                                    )
//                                })(),
//                                label: {
//                                    textStyle: {
//                                        color: 'rgba(30,144,255,0.8)',
//                                        align: 'center',
//                                        baseline: 'middle',
//                                        fontFamily: 'Open Sans',
//                                        fontSize: 30,
//                                        fontWeight: '700'
//                                    }
//                                },
//                                labelLine: {
//                                    length: 40,
//                                    lineStyle: {
//                                        color: '#f0f',
//                                        width: 3,
//                                        type: 'dotted'
//                                    }
//                                }
//                            }
//                        }
//                    }, {
//                        value: 251,
//                        name: 'Google'
//                    }, {
//                        value: 102,
//                        name: 'Bing',
//                        itemStyle: {
//                            normal: {
//                                label: {
//                                    show: false
//                                },
//                                labelLine: {
//                                    show: false
//                                }
//                            },
//                            emphasis: {
//                                label: {
//                                    show: true
//                                },
//                                labelLine: {
//                                    show: true,
//                                    length: 50
//                                }
//                            }
//                        }
//                    }, {
//                        value: 147,
//                        name: 'Others'
//                    }]
//                }, {
//                    name: 'Source',
//                    type: 'pie',
//                    clockWise: true,
//                    startAngle: 135,
//                    center: ['75%', 200],
//                    radius: [80, 120],
//                    itemStyle: 　{
//                        normal: {
//                            label: {
//                                show: false
//                            },
//                            labelLine: {
//                                show: false
//                            }
//                        },
//                        emphasis: {
//                            color: (function() {
//                                var zrColor = require('zrender/tool/color');
//                                return zrColor.getRadialGradient(
//                                    650, 200, 80, 650, 200, 120, [
//                                        [0, 'rgba(255,255,0,1)'],
//                                        [1, 'rgba(255,0,0,1)']
//                                    ]
//                                )
//                            })(),
//                            label: {
//                                show: true,
//                                position: 'center',
//                                formatter: "{d}%",
//                                textStyle: {
//                                    color: 'red',
//                                    fontSize: '30',
//                                    fontFamily: 'Open Sans',
//                                    fontWeight: 'bold'
//                                }
//                            }
//                        }
//                    },
//                    data: [{
//                        value: 335,
//                        name: 'All'
//                    }, {
//                        value: 310,
//                        name: 'EDM'
//                    }, {
//                        value: 234,
//                        name: 'Partnership'
//                    }, {
//                        value: 135,
//                        name: 'Video'
//                    }, {
//                        value: 1548,
//                        name: 'Search'
//                    }],
//                    markPoint: {
//                        symbol: 'star',
//                        data: [{
//                            name: 'Max',
//                            value: 1548,
//                            x: '80%',
//                            y: 50,
//                            symbolSize: 32
//                        }]
//                    }
//                }]
//            });
//        }
//    );
//});

function indexCharts(id) {
    var myChart = echarts.init(document.getElementById(id));
    myChart.setOption({
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [
            {
                name: '（工作流创建天数）',
                type: 'category',
                boundaryGap: false,
                data: ['1', '5', '10', '15', '20', '25', '31']
            }
        ],
        yAxis: [
            {
                name: '（平台用户活跃度）',
                type: 'value',
                max: 100
            }
        ],
        series: [
            {
                name: '平台用户活跃度',
                type: 'line',
                tooltip: {
                    trigger: 'axis'
                    // formatter: '{a} <br/>{b}日: {c}元'
                },
                smooth: true,
                itemStyle: {
                    normal: {
                        color: 'rgba(2, 197, 233, 0.2)',
                        lineStyle: {
                            color: 'rgba(23, 107, 203, 0.2)'
                        },
                        areaStyle: {
                            color: 'rgba(223, 147, 233, 0.2)'
                        }
                    }
                },
                data: [10, 12, 21, 54, 60, 80, 71]
            },
            //{
            //    name: '天数',
            //    type: 'line',
            //    tooltip: {
            //        trigger: 'axis'
            //        // formatter: '{a} <br/>{b}日: {c}元'
            //    },
            //    yAxisIndex: 1,
            //    smooth: true,
            //    itemStyle: {
            //        normal: {
            //            color: 'rgba(2, 197, 233, 0.2)',
            //            lineStyle: {
            //                color: 'rgba(2, 197, 233, 0.2)'
            //            },
            //            areaStyle: {
            //                color: 'rgba(2, 197, 233, 0.2)'
            //            }
            //        }
            //    },
            //    data: [30, 32, 61, 24, 20, 90, 20]
            //}
        ]
    });
}

function circleCharts(id, title) {
    var myChart = echarts.init(document.getElementById(id));
    myChart.setOption({
        title : {
            text: title,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['运行失败','运行中','运行成功']
        },
        series : [
            {
                name: '运行概况',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:Math.floor((Math.random()*68)+1), name:'运行失败'},
                    {value:Math.floor((Math.random()*310)+100), name:'运行中'},
                    {value:Math.floor((Math.random()*1500)+1200), name:'运行成功'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
}

function ringCharts(id) {
    var circleChart = echarts.init(document.getElementById(id));
    var optionCircle = {
        title: {
            text: '80分',
            x: 'center',
            y: 'center',
            itemGap: 20,
            textStyle : {
                color : '#666',
                fontFamily : '微软雅黑',
                fontSize : 35,
                fontWeight : 'bolder'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        color:['#38bb9b','#32b4ff','#ffbe34'],
        legend: {
            x : 'center',
            y : 'bottom',
            data:['专利授权对象','专利引证数量','专利授权数量']
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['40%', '55%'],

                data:[
                    {value:40, name:'专利授权对象'},
                    {value:30, name:'专利引证数量'},
                    {value:20, name:'专利授权数量'}
                ]
            }
        ]
    };
    circleChart.setOption(optionCircle);
}
function lineCharts(id,d,maxPrice, minPrice) {
    var lineChart = echarts.init(document.getElementById(id));
    option = {
        title : {
            text: '下周蔬菜价格变化'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['最高价格']
        },
        // toolbox: {
        //     show: true,
        //     feature: {
        //         dataZoom: {
        //             yAxisIndex: 'none'
        //         },
        //         dataView: {readOnly: false},
        //         magicType: {type: ['line', 'bar']},
        //         restore: {},
        //         saveAsImage: {}
        //     }
        // },

        calculable : true,
        xAxis : [
            {
                type : 'category',
                name : '时间',
                boundaryGap : false,
                data : ['05-29','05-30','05-31','06-01','06-02','06-03','06-04']
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '价格 单位 元/公斤',
                max: maxPrice,
                min: minPrice,
                axisLabel : {
                    formatter: '{value}'
                }
            }
        ],
        series : [
            {
                name:'今日价格',
                type:'line',
                data:d,
                // color: '#32c5d2',
                markPoint : {
                    data : [
                        {type : 'max', name: '价格'}
                    ],
                    color:'#32c5d2'
                },
                //markLine: {
                //    data: [
                //        {type: 'average', name: '平均值'}
                //    ]
                //},
                itemStyle : {
                    normal : {
                        symbol: 'circle',
                        color: "#32c5d2",
                        lineStyle:{
                            color:'#32c5d2'
                        }
                    }
                }
            }
        ]
    };
    lineChart.setOption(option);
}

// 运维中心
function completeCharts(id,todayData,yesterdayData,avgData) {
    var completeCharts = echarts.init(document.getElementById(id));

    var option={
        tooltip: {
            trigger: 'axis',
            axisPointer:{
                type:'line',
                lineStyle:{
                    color: "#fff",
                    width:2
                }
            }
        },
        // legend: {
        //     data:['完成平均','完成','失败']
        // },
        grid:{
            x: 62,
            x2: 62,
            y: 30,
            y2: 60
            // height:170
        },
        xAxis : [
            {
                name:'(小时)',
                type : 'category',
                boundaryGap : false,
                nameTextStyle:{

                },
                axisLine: {
                    lineStyle: {color: '#43C4BC'}
                },
                axisLabel:{

                    margin: 20,

                },
                axisTick:{
                    show:false,
                },
                data : ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']
            }
        ],
        yAxis : [
            {
                name:'(个)',
                nameLocation:"end",
                nameTextStyle:{
                    verticalAlign:'top',
                    lineHeight: 100,
                },

                axisLine: {
                    show: false,
                    lineStyle: {color: '#fff',
                        width :2}
                }, // 控制网格线是否显示
                axisLabel:{
                    margin: 15,
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#43C4BC'
                    }
                }, // 去除y轴上的刻度线
                axisTick: {
                    show: false
                }, // show:false,
                type : 'value',
                nameGap:'5'
            }
        ],
        series : [
            {
                name:'历史平均完成',
                type:'line',
                // stack: '总量',
                smooth: true,
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 1,
                            color: '#20BEB0'
                        }])
                    }
                },
                itemStyle : {
                    normal : {
                        color:'#20BEB0',
                        lineStyle:{
                            color:'#20BEB0',
                            shadowColor: 'rgba(15, 129, 155, 0.37)',
                            shadowBlur: 5
                            // shadowOffsetY: -5
                        }
                    }
                },
                symbol: 'circle',
                symbolSize:0,
                data:avgData
            },
            {
                name:'昨天完成',
                type:'line',
                smooth: true,
                symbol:'circle',
                symbolSize:8,
                itemStyle : {
                    normal : {
                        color: "#21D6BB",  // 会设置点和线的颜色，所以需要下面定制 line
                        borderColor: "#21D6BB",  // 点边线的颜色
                        shadowColor: 'rgba(15, 129, 155, 0.35)',
                        shadowBlur: 10,
                        lineStyle:{
                            color:'#21D6BB',
                            shadowColor: 'rgba(15, 129, 155, 0.35)',
                            shadowBlur: 10
                        }
                    }
                },
                data:yesterdayData
            },
            {
                name:'今天完成',
                type:'line',
                smooth: true,
                symbol:'circle',
                symbolSize:16,
                itemStyle : {
                    normal : {
                        color: "#1BBD9C",  // 会设置点和线的颜色，所以需要下面定制 line
                        borderColor: "#fff",  // 点边线的颜色
                        borderWidth: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.32)',
                        shadowBlur: 4,
                        lineStyle:{
                            color:'#fff'
                            // shadowColor: 'rgba(0, 0, 0, 0.46)',
                            // shadowBlur: 10
                        }
                    },
                    emphasis: {
                        color: "#fff",
                        borderWidth: 20,
                        borderColor: 'rgba(255, 255, 255, 0.4)',
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(255, 255, 255, 0.5)'
                    }
                },
                data:todayData
            }
        ],
        backgroundColor: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
                offset: 0,
                color: '#1bb9ae' // 0% 处的颜色
            },{
                offset: .4,
                color: '#16a4a7' // 100% 处的颜色
            },{
                offset: 1,
                color: '#0f819b' // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
        }
    };

    completeCharts.setOption(option);
}
// 运行情况折线图
function zhexian(id,runData) {
    var zhexianCharts = echarts.init(document.getElementById(id));

    var option = {
        backgroundColor: '#fff',
        title: {

        },
        tooltip: {
            trigger: 'axis',
            axisPointer:{
                type:'line',
                lineStyle:{
                    color: "#1bbc9b",
                    width:2
                }
            }
        },
        xAxis:  {
            name:'(小时)',
            nameLocation:"end",
            type: 'category',
            boundaryGap: true,
            axisLine: {
                lineStyle: {color: '#43C4BC'}
            },
            axisTick:{
                show:false,
            },
            data : ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']
        },
        yAxis: {
            name:'(个)',
            nameLocation:"end",
            minInterval: 1,
            splitNumber:5,
            min:0,
            axisLine: {
                show: false,
                lineStyle: {color: '#43C4BC'}
            }, // 控制网格线是否显示

            splitLine: {
                show: true,
                lineStyle: {
                    color: '#43C4BC'
                }
            }, // 去除y轴上的刻度线
            axisTick: {
                show: false
            }, // show:false,
            type : 'value',
            nameGap:'25',

        },
        series: [
            {
                name:'正在运行数量',
                type:'line',
                smooth: true,
                showSymbol: false,
                symbol: false,
                lineStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                            offset: 0.2, color: '#1AB99A' // 0% 处的颜色
                        }, {
                            offset: 0.4, color: '#0F849C' // 0% 处的颜色
                        },{
                            offset: 0.7, color: '#1AB99A' // 0% 处的颜色
                        },{
                            offset: 1, color: '#0F849C' // 100% 处的颜色
                        }], false),
                        width: 5
                    },
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0.2,
                            color: '#90DECE'
                        },{
                            offset: 0.6,
                            color: '#D9F4EF'
                        },{
                            offset: 1,
                            color: '#fff'
                        }])
                    }
                },
                itemStyle : {
                    normal : {
                        color: "#fff",  // 会设置点和线的颜色，所以需要下面定制 line
                        borderColor: "#fff",  // 点边线的颜色
                        lineStyle:{
                            color:'#fff'
                        }
                    },
                    emphasis: {
                        color: "#fff",
                        borderWidth: 6,
                        borderColor: 'rgba(255, 255, 255, 1)',
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(26, 185, 154, 1)'
                    }
                },
                data: runData
            }
        ]
    };
    zhexianCharts.setOption(option);
}
function tiaoxin(id,weekData,resultData) {
    var tiaoxins = echarts.init(document.getElementById(id));
    tiaoxins.setOption( {

        tooltip: {
            trigger: 'axis',
            axisPointer:{
                type:'line',
                lineStyle:{
                    color: "#1bbc9b",
                    width:2
                }
            }
        },
        backgroundColor: '#fff',

        calculable : true,
        xAxis : [
            {
                name:"(年-月-日)",
                nameLocation:"middle",
                nameGap:20,
                nameLocation:"end",
                axisLine: {
                    show:false,
                    lineStyle: {color: '#43C4BC'}
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#bae9e3'

                    }
                },
                axisTick: {
                    show: false
                },

                type : 'category',
                data : weekData
            }
        ],
        yAxis : [
            {
                name:"(个)",
                nameLocation:"end",
                nameGap:0,
                axisLine: {
                    show: false,
                    lineStyle: {color: '#43C4BC'}
                }, // 控制网格线是否显示

                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#bae9e3'
                    }
                }, // 去除y轴上的刻度线
                axisTick: {
                    show: false
                }, // show:false,
                type : 'value',
                nameGap:'25'
            }
        ],
        series : [
            {
                name:'任务数',
                barWidth: '50%',
                type:'bar',
                data:resultData

            }
        ],
        itemStyle: {
            normal: {

                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(29, 189,163, 4)'

                }, {
                    offset: 1,
                    color: 'rgba(16, 136,157, 1)'
                }]),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        }


    });

}