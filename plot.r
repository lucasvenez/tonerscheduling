require(ggplot2)

setwd("/home/lucas/git/tonerscheduling/")

ddd <- read.table("performance_analysis.csv", sep = ",", dec = ".", header = T)

ddd$n = ddd$n - 1
ddd$m = ddd$m - 1
ddd$n_x_m = ddd$n * ddd$m

ddd <- ddd[-1]

ddd <- ddd[ddd$time > 0,]

ddd <- aggregate(time ~ n_x_m, data = ddd, mean)

ddd <- ddd[with(ddd, order(time)),]

head(ddd)

plot <- ggplot(ddd, aes(x=n_x_m, y=time)) + geom_line(size=1.4) + 
          xlab("Tamanho da Instância (n x m)") + 
          ylab("Maior Número de Tonéis Esperando em Produção") +
          theme(axis.text.x = element_text(size=10))

ggsave("/home/lucas/git/tonerscheduling/output/time.png", units = "cm", width = 13, height = 10)