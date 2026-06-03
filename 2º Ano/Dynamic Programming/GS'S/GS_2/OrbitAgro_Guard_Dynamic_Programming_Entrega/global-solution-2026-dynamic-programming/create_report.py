from __future__ import annotations

import json
from pathlib import Path
from textwrap import wrap

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4, landscape
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.platypus import (
    BaseDocTemplate, Frame, PageTemplate, Paragraph, Spacer, Table, TableStyle,
    Image, PageBreak, KeepTogether
)

ROOT = Path(__file__).resolve().parent
summary = json.loads((ROOT / 'results/analysis_summary.json').read_text(encoding='utf-8'))
fig = ROOT / 'figures'
out = ROOT / 'report/relatorio_final.pdf'

PAGE_W, PAGE_H = landscape(A4)
MARGIN = 0.9 * cm

styles = getSampleStyleSheet()
styles.add(ParagraphStyle(name='TitleCustom', fontName='Helvetica-Bold', fontSize=17, leading=20, alignment=TA_CENTER, textColor=colors.HexColor('#12355B'), spaceAfter=6))
styles.add(ParagraphStyle(name='SubTitle', fontName='Helvetica-Bold', fontSize=9.5, leading=12, alignment=TA_CENTER, textColor=colors.HexColor('#2E5E4E'), spaceAfter=6))
styles.add(ParagraphStyle(name='H1Custom', fontName='Helvetica-Bold', fontSize=10.5, leading=12, textColor=colors.HexColor('#12355B'), spaceBefore=5, spaceAfter=3))
styles.add(ParagraphStyle(name='H2Custom', fontName='Helvetica-Bold', fontSize=8.7, leading=10.2, textColor=colors.HexColor('#2E5E4E'), spaceBefore=3, spaceAfter=2))
styles.add(ParagraphStyle(name='BodyCustom', fontName='Helvetica', fontSize=7.4, leading=9.1, alignment=TA_LEFT, spaceAfter=2))
styles.add(ParagraphStyle(name='Small', fontName='Helvetica', fontSize=6.45, leading=7.5, spaceAfter=1.5))
styles.add(ParagraphStyle(name='Caption', fontName='Helvetica-Oblique', fontSize=6.35, leading=7.4, textColor=colors.HexColor('#333333'), spaceAfter=3))
styles.add(ParagraphStyle(name='Tiny', fontName='Helvetica', fontSize=5.9, leading=6.8, spaceAfter=1))


def p(text, style='BodyCustom'):
    text = text.replace('±', '+/-')
    return Paragraph(text, styles[style])


def img(path: Path, w_cm: float, h_cm: float):
    return Image(str(path), width=w_cm * cm, height=h_cm * cm)


def caption(title: str, interpretation: str):
    return p(f'<b>{title}</b> Fonte: dados sinteticos justificados do OrbitAgro Guard, gerados a partir de custo logistico, risco r[i][j], probabilidade p[i][j] e bloqueios. Interpretacao: {interpretation}', 'Caption')


def header_footer(canvas, doc):
    canvas.saveState()
    canvas.setFillColor(colors.HexColor('#12355B'))
    canvas.rect(0, PAGE_H - 0.35 * cm, PAGE_W, 0.35 * cm, stroke=0, fill=1)
    canvas.setFillColor(colors.white)
    canvas.setFont('Helvetica-Bold', 7)
    canvas.drawString(MARGIN, PAGE_H - 0.25 * cm, 'OrbitAgro Guard - Dynamic Programming | Global Solution 2026')
    canvas.setFillColor(colors.HexColor('#555555'))
    canvas.setFont('Helvetica', 6.5)
    canvas.drawRightString(PAGE_W - MARGIN, 0.35 * cm, f'Pagina {doc.page} de 4')
    canvas.restoreState()


doc = BaseDocTemplate(
    str(out), pagesize=landscape(A4),
    leftMargin=MARGIN, rightMargin=MARGIN, topMargin=0.62 * cm, bottomMargin=0.55 * cm
)
frame = Frame(doc.leftMargin, doc.bottomMargin, doc.width, doc.height, id='normal')
doc.addPageTemplates([PageTemplate(id='p', frames=[frame], onPage=header_footer)])

story = []

# Page 1
story.append(p('OrbitAgro Guard', 'TitleCustom'))
story.append(p('Relatorio Tecnico - Dynamic Programming | Otimizacao de Rotas de Resposta a Riscos Agroclimaticos', 'SubTitle'))
team_rows = [[p('<b>Integrante</b>', 'Small'), p('<b>RM</b>', 'Small')]] + [[p(t['name'], 'Small'), p(t['rm'], 'Small')] for t in summary['team']]
team_tbl = Table(team_rows, colWidths=[9.0 * cm, 2.2 * cm])
team_tbl.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#DDECE5')),
    ('GRID', (0,0), (-1,-1), 0.25, colors.HexColor('#A6A6A6')),
    ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ('LEFTPADDING', (0,0), (-1,-1), 4), ('RIGHTPADDING', (0,0), (-1,-1), 4),
]))
story.append(team_tbl)
story.append(Spacer(1, 4))
story.append(p('1. Contextualizacao e justificativa', 'H1Custom'))
story.append(p('O OrbitAgro Guard e uma solucao de economia espacial aplicada ao campo: converte dados orbitais e agroclimaticos em rotas de resposta para pequenos produtores e comunidades rurais com baixa conectividade. A implementacao usa dois cenarios brasileiros solicitados no enunciado: (A) seca no Cerrado/Nordeste - MATOPIBA e (D) conectividade rural via satelite. Como prototipo academico, os dados sao sinteticos justificados e reproduziveis; a estrutura permite substituir as matrizes por fontes reais como NASA, INPE, INMET, ANA, IBGE e ANATEL.', 'BodyCustom'))
story.append(p('A decisao por MATOPIBA prioriza estiagem severa e perdas agricolas; a decisao por conectividade rural prioriza implantacao de infraestrutura LEO em areas desassistidas. Em ambos, cada celula da grade N x M representa municipio/celula territorial com custo c[i][j], risco r[i][j], probabilidade historica p[i][j] e bloqueios logisticos.', 'BodyCustom'))
story.append(p('2. Formulacao matematica e recorrencia DP', 'H1Custom'))
story.append(p('O custo efetivo harmoniza custo logistico e prioridade social: eff[i][j] = c[i][j] * (1 + 0,45r[i][j] + 0,35p[i][j]) * (1 - 0,25r[i][j]p[i][j]). Celulas bloqueadas recebem -1 e sao intransponiveis. Para movimentos apenas para direita ou baixo, a recorrencia e:', 'BodyCustom'))
story.append(p('<b>dp[i][j] = eff[i][j] + min(dp[i-1][j], dp[i][j-1])</b>, com dp[0][0]=eff[0][0]. Primeira linha e primeira coluna acumulam somente a direcao valida. O destino pode ser o canto inferior direito ou qualquer celula da ultima linha/coluna, conforme extensao pedida. A subestrutura otima ocorre porque qualquer rota otima ate (i,j) precisa vir de uma rota otima ate (i-1,j) ou (i,j-1); se esse prefixo nao fosse otimo, substituir por prefixo melhor reduziria o custo total, contradizendo a otimalidade. A sobreposicao aparece porque subproblemas dp[i][j] sao reutilizados por varios destinos sucessores.', 'BodyCustom'))
story.append(p('3. Complexidade teorica', 'H1Custom'))
complexity = [
    [p('<b>Algoritmo</b>', 'Small'), p('<b>Tempo</b>', 'Small'), p('<b>Espaco</b>', 'Small'), p('<b>Papel</b>', 'Small')],
    [p('Forca Bruta', 'Small'), p('O(C(n+m,n)) / exponencial', 'Small'), p('O(n+m)', 'Small'), p('Oraculo para N,M <= 5; valida a DP.', 'Small')],
    [p('DP Bottom-Up', 'Small'), p('O(N*M)', 'Small'), p('O(N*M)', 'Small'), p('Solucao otima tabular, reconstrucao por backtracking.', 'Small')],
    [p('DP Top-Down', 'Small'), p('O(N*M)', 'Small'), p('O(N*M)', 'Small'), p('Memoizacao; mesmo custo assintotico, mais overhead recursivo.', 'Small')],
    [p('Monte Carlo + DP', 'Small'), p('O(K*N*M)', 'Small'), p('O(K*N*M) vetorizado ou O(N*M) iterativo', 'Small'), p('Incerteza climatica com K >= 10.000 e IC 95%.', 'Small')],
]
ct = Table(complexity, colWidths=[3.7*cm, 4.2*cm, 4.2*cm, 13.0*cm])
ct.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),colors.HexColor('#DDECE5')),('GRID',(0,0),(-1,-1),0.25,colors.HexColor('#A6A6A6')),('VALIGN',(0,0),(-1,-1),'TOP')]))
story.append(ct)
story.append(PageBreak())

# Page 2 figures 1,3,4
story.append(p('4. Resultados - desempenho e Programacao Dinamica', 'H1Custom'))
row = [
    [img(fig/'fig_01_tradeoff_scatter.png', 8.6, 5.7), img(fig/'fig_03_heatmap_dp_path.png', 8.6, 5.7), img(fig/'fig_04_scalability.png', 8.6, 5.7)],
    [caption('Figura 1 - Custo otimo x tempo computacional.', 'A forca bruta valida pequenas instancias, mas se torna inviavel conforme N cresce. A DP mantem tempo baixo e custo otimo consistente. O Monte Carlo custa mais porque executa varias DP para estimar incerteza, logo seu tempo reflete K cenarios.'),
     caption('Figura 3 - Heatmap da tabela DP com caminho otimo.', 'O mapa mostra o custo minimo acumulado na grade MATOPIBA. O caminho destacado evita bloqueios e regioes de maior custo efetivo. A visualizacao confirma a reconstrucao por backtracking e facilita explicar a decisao operacional.'),
     caption('Figura 4 - Escalabilidade empirica.', 'A curva da DP acompanha o crescimento quadratico N*M esperado. A forca bruta so e executada ate N=5 e depois aparece como estimativa, pois o numero de caminhos explode. O Monte Carlo cresce com K*N*M, justificando paralelizacao/vetorizacao.')]
]
t = Table(row, colWidths=[8.8*cm, 8.8*cm, 8.8*cm], rowHeights=[5.9*cm, 2.6*cm])
t.setStyle(TableStyle([('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),2),('RIGHTPADDING',(0,0),(-1,-1),2)]))
story.append(t)
story.append(Spacer(1, 4))
validation = summary['validation']
story.append(p(f'Validacao: na instancia 5x5, a Forca Bruta encontrou custo {validation["brute_force_cost"]:.2f} e a DP encontrou {validation["dp_cost"]:.2f}; os valores coincidiram ({validation["same_cost"]}). Foram registradas {validation["brute_force_calls"]} chamadas recursivas e {validation["paths_evaluated"]} caminhos finais avaliados apos podas exatas.', 'Small'))
story.append(PageBreak())

# Page 3 figures 2,5 + stats
story.append(p('5. Monte Carlo, incerteza e sensibilidade', 'H1Custom'))
row = [
    [img(fig/'fig_02_monte_carlo_hist_box.png', 12.4, 7.0), img(fig/'fig_05_sensitivity.png', 12.4, 7.0)],
    [caption('Figura 2 - Histograma e boxplot Monte Carlo.', 'Foram executados 10.000 cenarios na amostra MATOPIBA 20x20. A distribuicao compacta indica que a rota otima e estavel sob variacao Beta de p[i][j]. O boxplot ajuda a identificar dispersao e eventuais cenarios extremos para planejamento de contingencia.'),
     caption('Figura 5 - Sensibilidade p[i][j] +/- 20%.', 'Aumento de probabilidade eleva o custo medio simulado porque o risco operacional fica mais intenso. A queda de 20% reduz o custo esperado, mas nao elimina a necessidade de resposta. O grafico mostra robustez: a politica recomendada continua viavel nos tres casos.')]
]
t = Table(row, colWidths=[13.0*cm, 13.0*cm], rowHeights=[7.2*cm, 2.3*cm])
t.setStyle(TableStyle([('VALIGN',(0,0),(-1,-1),'TOP'),('LEFTPADDING',(0,0),(-1,-1),2),('RIGHTPADDING',(0,0),(-1,-1),2)]))
story.append(t)
mc = summary['monte_carlo_matopiba_20x20']
sens = summary['sensitivity_matopiba_20x20']
story.append(Spacer(1, 3))
stat_rows = [[p('<b>Metrica</b>','Small'), p('<b>Valor</b>','Small'), p('<b>Sensibilidade</b>','Small'), p('<b>Custo medio</b>','Small'), p('<b>IC 95%</b>','Small')],
             [p('Media MC baseline','Small'), p(f'{mc["mean"]:.2f}','Small'), p('-20%','Small'), p(f'{sens["-20%"]["mean"]:.2f}','Small'), p(f'{sens["-20%"]["ci95_low"]:.2f} - {sens["-20%"]["ci95_high"]:.2f}','Small')],
             [p('Mediana','Small'), p(f'{mc["median"]:.2f}','Small'), p('baseline','Small'), p(f'{sens["baseline"]["mean"]:.2f}','Small'), p(f'{sens["baseline"]["ci95_low"]:.2f} - {sens["baseline"]["ci95_high"]:.2f}','Small')],
             [p('Desvio padrao','Small'), p(f'{mc["std"]:.2f}','Small'), p('+20%','Small'), p(f'{sens["+20%"]["mean"]:.2f}','Small'), p(f'{sens["+20%"]["ci95_low"]:.2f} - {sens["+20%"]["ci95_high"]:.2f}','Small')]]
st = Table(stat_rows, colWidths=[5.1*cm, 3.2*cm, 3.0*cm, 3.5*cm, 7.2*cm])
st.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),colors.HexColor('#DDECE5')),('GRID',(0,0),(-1,-1),0.25,colors.HexColor('#A6A6A6')),('VALIGN',(0,0),(-1,-1),'TOP')]))
story.append(st)
story.append(PageBreak())

# Page 4 decision / conclusion / references
story.append(p('6. Escala de decisao comentada e comparativa', 'H1Custom'))
decision = [
    [p('<b>Nivel</b>','Small'), p('<b>Alternativa</b>','Small'), p('<b>Justificativa tecnica</b>','Small')],
    [p('4 - Recomendada','Small'), p('DP Bottom-Up + Monte Carlo para MATOPIBA','Small'), p('Melhor equilibrio entre otimalidade, previsibilidade e robustez. A DP entrega caminho otimo em O(N*M) e o MC quantifica risco com IC 95%, apoiando politica publica de resposta a seca.', 'Small')],
    [p('3 - Viavel','Small'), p('DP Bottom-Up para Conectividade Rural','Small'), p('Aplicavel para expansao de infraestrutura LEO em areas rurais. Baixo custo computacional e boa aderencia ao ODS 9, mas depende de dados reais de cobertura e investimento.', 'Small')],
    [p('2 - Complementar','Small'), p('DP Top-Down com memoizacao','Small'), p('Produz o mesmo custo otimo, porem com overhead recursivo e risco de limite de pilha em grades grandes. E util para demonstrar a recorrencia e validar a tabulacao.', 'Small')],
    [p('1 - Apenas validacao','Small'), p('Forca Bruta','Small'), p('Importante como oraculo para N,M <= 5, mas inviavel em escala real por crescimento combinatorio. Nao deve ser usada na operacao.', 'Small')],
]
dt = Table(decision, colWidths=[3.1*cm, 6.5*cm, 16.0*cm])
dt.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),colors.HexColor('#DDECE5')),('GRID',(0,0),(-1,-1),0.25,colors.HexColor('#A6A6A6')),('VALIGN',(0,0),(-1,-1),'TOP')]))
story.append(dt)
story.append(Spacer(1, 3))
story.append(p('7. Conclusao e recomendacao de politica publica', 'H1Custom'))
story.append(p('A recomendacao e implantar o OrbitAgro Guard como piloto em polos cooperativos e escolas rurais conectadas via satelite no MATOPIBA. A politica publica sugerida e priorizar celulas com alto risco, alta probabilidade de ocorrencia e menor custo logistico marginal, usando DP para definir corredores de atendimento e Monte Carlo para dimensionar recursos sob incerteza climatica. A solucao se conecta aos ODS 2, 8, 9, 11 e 13 por reduzir perdas agricolas, apoiar renda local, expandir infraestrutura, fortalecer comunidades e responder a eventos climaticos extremos.', 'BodyCustom'))
story.append(p('8. Referencias', 'H1Custom'))
refs = [
    'Bellman, R. (1957). Dynamic Programming. Princeton University Press.',
    'Cormen, T. et al. (2022). Introduction to Algorithms, 4th Ed. MIT Press.',
    'Kroese, D. P. et al. (2011). Handbook of Monte Carlo Methods. Wiley.',
    'NASA Earthdata / MODIS NDVI / SRTM: earthdata.nasa.gov.',
    'INPE PRODES/DETER: terrabrasilis.dpi.inpe.br; INMET BDMEP: bdmep.inmet.gov.br; ANA HidroWeb: hidroweb.ana.gov.br; ANATEL Mapa: mapa.anatel.gov.br.',
    'Enunciado FIAP Global Solution 2026 - Dynamic Programming, turma B.'
]
for r in refs:
    story.append(p('• ' + r, 'Small'))

# Build
doc.build(story)
print(out)
