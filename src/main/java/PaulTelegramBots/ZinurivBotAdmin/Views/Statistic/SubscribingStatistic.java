package PaulTelegramBots.ZinurivBotAdmin.Views.Statistic;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.BarChartConfig;
import com.byteowls.vaadin.chartjs.data.BarDataset;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class SubscribingStatistic extends VerticalLayout{
	
	public static ChartJs getChart(String userName) {
		
		HashMap<String, Long> subStat = DAO.Statistics.getSubscribeStat(userName);
		HashMap<String, Long> unSubStat = DAO.Statistics.getUnSubscribeStat(userName);
		
		Set<String> setKeys = new TreeSet(subStat.keySet());
		Set<String> unsubKeys =  new TreeSet(unSubStat.keySet());
		
		setKeys.addAll(unsubKeys);
		TreeSet<String> sortedKeys = new TreeSet<String>(setKeys);
		
		ArrayList<Double> subValues = new ArrayList<>();
		ArrayList<Double> unsubValues = new ArrayList<>();
		
		for(String key : sortedKeys) {
			if(subStat.containsKey(key))
				subValues.add(subStat.get(key).doubleValue());
			else
				subValues.add(0.0);
			
			if(unSubStat.containsKey(key))
				unsubValues.add(unSubStat.get(key).doubleValue());
			else
				unsubValues.add(0.0);
			
		}
		
		BarChartConfig barConfig = new BarChartConfig();
        barConfig.
            data()
                .labelsAsList(setKeys.stream().sorted().collect(Collectors.toList()))
                .addDataset(
                        new BarDataset().backgroundColor("rgba(0,255,20,0.5)").label("Подписались").yAxisID("y-axis-1").dataAsList(subValues))
                .addDataset(
//                        new BarDataset().backgroundColor("rgba(206,7,170,0.5)").label("Отписалось").yAxisID("y-axis-2").hidden(true))
                		new BarDataset().backgroundColor("rgba(255,0,20,0.5)").label("Отписались").yAxisID("y-axis-1").dataAsList(unsubValues))
                .and();
        barConfig.
            options()
                .responsive(true)
                .hover()
                    .mode(InteractionMode.INDEX)
                    .intersect(true)
                    .animationDuration(400)
                    .and()
                .title()
                    .display(true)
                    .fullWidth(true)
                    .text("Динамика подписок/отписок по датам")
                    .and()
                .scales()
                    .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
//                    .add(Axis.Y, new LinearScale().display(true).position(Position.RIGHT).id("y-axis-2").gridLines().drawOnChartArea(false).and())
                    .and()
               .done();
/*
        List<String> labels = barConfig.data().getLabels();
        for (Dataset<?, ?> ds : barConfig.data().getDatasets()) {
            BarDataset lds = (BarDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100));
            }
            lds.dataAsList(data);
        }
*/
        ChartJs chart = new ChartJs(barConfig);
        return chart;
        
        				
		
	}
}
