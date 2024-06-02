insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Pasta alla Carbonara', 'PastaCarbonara.png',   15, 'Primo',    'Facile',   'La Carbonara è un primo piatto romano con spaghetti, guanciale, pecorino, uova, pepe e sale. Un tripudio di sapori: il guanciale croccante, la cremosità delle uova, il pecorino saporito. Da provare!');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Pasta Cacio e Pepe',   'CacioPepe.png',        10, 'Primo',    'Medio',    'Un classico romano semplice ma gustoso: spaghetti conditi con pecorino romano grattugiato, pepe nero macinato fresco e acqua di cottura per creare una cremina densa.');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Pizza Margherita',     'PizzaMargherita.png',  10, 'Primo',    'Medio',    'Un icona della cucina italiana: pomodoro, mozzarella fior di latte, basilico fresco e olio extravergine su una base di pizza croccante.');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Pasta alla Norma',     'PastaNorma.png',       15, 'Primo',    'Difficile','Un piatto siciliano ricco e saporito: melanzane fritte, ricotta salata, pomodoro e basilico su pasta al forno.');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Pasta Amatriciana',    'PastaAmatriciana.png', 10, 'Primo',    'Medio',    'Un primo piatto romano con guanciale, pecorino romano, pomodoro, cipolla, peperoncino e vino bianco.');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Bistecca di Manzo',    'BisteccaManzo.png',    25, 'Secondo',  'Medio',    'Un secondo piatto classico e versatile: carne di manzo cotta alla griglia o alla piastra, servita con i tuoi contorni preferiti.');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Ravioli Cinesi',       'RavioliCinesi.png',    20, 'Antipasto','Difficile','I ravioli cinesi, gustosi fagottini di pasta ripieni, simbolo di fortuna. Da provare al vapore, con salsa di soia e aceto di riso!');
insert into recipe (id, name, url_image, cook_time, recipe_type, difficulty, description) values(nextval('recipe_seq'), 'Polpette al Sugo',     'PolpetteSugo.png',     15, 'Secondo',  'Medio',    'Polpette al sugo: sfiziose polpettine di carne in un sugo di pomodoro, un classico italiano da gustare!');


insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Carlo',      'Cracco',           'CarloCracco.png',             '8-10-1965');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Massimo',    'Bottura',          'MassimoBottura.png',          '30-09-1962');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Joe',        'Bastianich',       'JoeBastianich.png',           '17-09-1968');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Enrico',     'Crippa',           'EnricoCrippa.png',            '25-11-1971');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Antonino',   'Cannavacciuolo',   'AntoninoCannavacciuolo.png',  '16-04-1975');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Bruno',      'Barbieri',         'BrunoBarbieri.png',           '12-01-1962');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Alessandro', 'Borghese',         'AlessandroBorghese.png',      '19-11-1976');
insert into cook (id, name, surname, url_image, year) values(nextval('cook_seq'), 'Gordon',     'Ramsay',           'GordonRamsay.png',            '8-11-1966')


insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Uova',             'Un alimento altamente nutriente e versatile, l uovo è una fonte di proteine di alta qualità. È comunemente usato nella cucina per preparare piatti dolci e salati, come torte, frittate, e salse', 'uova.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Guanciale',        'Un tipo di pancetta italiana che viene preparata utilizzando il grasso e la carne della guancia del maiale. Ha un sapore intenso e aromatico ed è spesso utilizzato come ingrediente principale nella famosa pasta all amatriciana e in altre ricette della cucina italiana', 'guanciale.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Pecorino',         'Il Pecorino Romano è un formaggio italiano stagionato, a pasta dura e dal sapore robusto e salato. Viene grattugiato su piatti come la pasta cacio e pepe o la pasta alla carbonara per aggiungere gusto e complessità', 'pecorino.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Spaghetti',        'Un alimento base della cucina italiana, la pasta è costituita principalmente da farina di grano e acqua. Può essere accompagnata da una vasta gamma di salse e condimenti', 'spaghetti.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Aglio',            'Un bulbo aromatico dal gusto pungente, utilizzato per insaporire salse, soffritti e piatti di carne e pesce.', 'aglio.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Broccoli',         'Infiorescenze verdi commestibili, ricche di vitamine e fibre, ideali per primi piatti, contorni e sformati.', 'broccoli.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Carote',           'Radici arancioni croccanti e dolciastre, ricche di betacarotene, perfette per insalate, minestroni e stufati.', 'carote.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Cipolla Bianca',   'Un ortaggio dal sapore delicato, utilizzato per soffritti, zuppe e torte salate.', 'cipollaBianca.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Cipolla Rossa',    'Un ortaggio dal sapore più intenso rispetto alla cipolla bianca, ideale per insalate, pinzimoni e conserve.', 'cipollaRossa.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Insalata',         'Verdure a foglia fresca, come lattuga, rucola o valeriana, consumate crude in insalate o come contorno.', 'insalata.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Limone',           'Un agrume dal gusto aspro e rinfrescante, utilizzato per condire salse, pesce e carne, e per preparare granite e limonate.', 'limone.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Patate',           'Tuberi amidacei, versatili in cucina, possono essere bolliti, arrostiti, fritti o utilizzati per gnocchi e purè.', 'patate.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Pomodori',         'Frutti rossi succosi e dal sapore dolce, consumati freschi in insalata, oppure utilizzati per preparare sughi, salse e conserve.', 'pomodori.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Zucchine',         'Ortaggi estivi dal sapore delicato, ideali per grigliate, frittate, pasta e minestre.', 'zucchine.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Sale Fino',        'Un condimento cristallino utilizzato per esaltare il sapore dei cibi.', 'saleFino.png');
insert into ingredient (id, name, description, url_image) values(nextval('ingredient_seq'), 'Sale Grosso',      'Un sale in grani più grandi, utilizzato per salare acqua di cottura, carne o verdure prima della cottura.', 'saleGrosso.png');


