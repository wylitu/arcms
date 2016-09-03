$('#searchForm input[name="days"]').click(function(){
    var selected = $(this).val();
    if (selected == "7"){
        $('.7_days_info').show();
        $('.15_days_info').hide();
        $('.30_days_info').hide();
    }else if (selected == "15"){
        $('.7_days_info').hide();
        $('.15_days_info').show();
        $('.30_days_info').hide();
    }else if (selected == "30"){
        $('.7_days_info').hide();
        $('.15_days_info').hide();
        $('.30_days_info').show();
    }
});

/**
 * 最近7天积分兑换情况
 */
$('#7-interactionChart').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: '最近7天积分兑换图'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
            '1日',
            '2日',
            '3日',
            '4日',
            '5日',
            '6日',
            '7日'
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: '数量'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
            +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [
        {
            name: '兑换人数',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6]

        },
        {
            name: '兑换积分数',
            data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0]
        }
    ]
});

/**
 * 最近15天积分兑换情况
 */
$('#15-interactionChart').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: '最近15天积分兑换图'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
            '1日',
            '2日',
            '3日',
            '4日',
            '5日',
            '6日',
            '7日',
            '8日',
            '9日',
            '10日',
            '11日',
            '12日',
            '13日',
            '14日',
            '15日'
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: '数量'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
            +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [
        {
            name: '兑换人数',
            data: [49.9, 71.5, 106.4, 129.2, 144.0,49.9, 71.5, 106.4, 129.2, 144.0,49.9,
                71.5, 106.4, 129.2, 144.0]

        },
        {
            name: '兑换积分数',
            data: [83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8,
                98.5, 93.4, 106.0 ]

        }
    ]
});

/**
 * 最近30天积分兑换情况
 */
$('#30-interactionChart').highcharts({
    chart: {
        type: 'column'
    },
    title: {
        text: '最近30天积分兑换图'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
            '1日',
            '2日',
            '3日',
            '4日',
            '5日',
            '6日',
            '7日',
            '8日',
            '9日',
            '10日',
            '11日',
            '12日',
            '13日',
            '14日',
            '15日',
            '16日',
            '17日',
            '18日',
            '19日',
            '20日',
            '21日',
            '22日',
            '23日',
            '24日',
            '25日',
            '26日',
            '27日',
            '28日',
            '29日',
            '30日'
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: '数量'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
            +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [
        {
            name: '兑换人数',
            data: [49.9, 71.5, 106.4, 129.2, 144.0,49.9, 71.5, 106.4, 129.2, 144.0,49.9,
                71.5, 106.4, 129.2, 144.0,
                49.9, 71.5, 106.4, 129.2, 144.0,49.9, 71.5, 106.4, 129.2, 144.0,49.9, 71.5,
                106.4, 129.2, 144.0]

        },
        {
            name: '兑换积分数',
            data: [83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8,
                98.5, 93.4, 106.0,
                83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8, 98.5, 93.4, 106.0,83.6, 78.8,
                98.5, 93.4, 106.0]

        }
    ]
});


