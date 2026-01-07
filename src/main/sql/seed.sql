SET FOREIGN_KEY_CHECKS = 0;

-- /////////////////////////////////
-- FLUSH TABLES (children -> parents)
-- /////////////////////////////////


DELETE FROM `order_line`;

ALTER TABLE `order_line` AUTO_INCREMENT = 1;


DELETE FROM `order`;

ALTER TABLE `order` AUTO_INCREMENT = 1;


DELETE FROM `cart_item`;

ALTER TABLE `cart_item` AUTO_INCREMENT = 1;


DELETE FROM `cart`;

ALTER TABLE `cart` AUTO_INCREMENT = 1;


DELETE FROM `training`;

ALTER TABLE `training` AUTO_INCREMENT = 1;


DELETE FROM `client`;

ALTER TABLE `client` AUTO_INCREMENT = 1;


DELETE FROM `user_account`;

ALTER TABLE `user_account` AUTO_INCREMENT = 1;

-- /////////////////////////////////
-- SEED (parents -> children)
-- /////////////////////////////////


INSERT INTO
    `user_account` (
        `id`,
        `login`,
        `password_hash`
    )
VALUES (
        1,
        'cloud_strife',
        'Cloud'
    ),
    (
        2,
        'sephiroth99',
        'Sephiroth'
    ),
    (
        3,
        'arthas_lich',
        'Arthas'
    ),
    (
        4,
        'thrall_warchief',
        'Thrall'
    ),
    (
        5,
        'eleven_012',
        '11'
    ),
    (
        6,
        'hopper_chief',
        'Hopper'
    ),
    (
        7,
        'naruto_hokage',
        'Naruto'
    ),
    (
        8,
        'sasuke_uchiha',
        'Sasuke'
    ),
    (
        9,
        'luffy_pirate',
        'Luffy'
    ),
    (
        10,
        'zoro_swordsman',
        'Zoro'
    ),
    (
        11,
        'nami_navigator',
        'Nami'
    ),
    (
        12,
        'sanji_chef',
        'Sanji'
    ),
    (
        13,
        'yshtola_scholar',
        'Yshtola'
    ),
    (
        14,
        'thancred_gunbreaker',
        'Thancred'
    ),
    (
        15,
        'alphinaud_sage',
        'Alphinaud'
    ),
    (
        16,
        'jaina_mage',
        'Jaina'
    ),
    (
        17,
        'sylvanas_dark',
        'Sylvanas'
    ),
    (
        18,
        'kakashi_sensei',
        'Kakashi'
    ),
    (
        19,
        'sakura_medic',
        'Sakura'
    ),
    (
        20,
        'dustin_henderson',
        'Dustin'
    );



INSERT INTO
    `client` (
        `first_name`,
        `last_name`,
        `email`,
        `address`,
        `phone`
    )
VALUES (
        'Max',
        'Mayfield',
        'max.mayfield47@yahoo.com',
        '773 Hawkins, Indiana 47201',
        '+1-517-114-1516'
    ),
    (
        'Kakashi',
        'Hatake',
        'kakashi.hatake987@protonmail.com',
        '809 Konohagakure, Land of Fire',
        '+1-255-689-3409'
    ),
    (
        'Will',
        'Byers',
        'will.byers52@outlook.com',
        '659 Hawkins, Indiana 47202',
        '+1-856-450-9342'
    ),
     (
        'Jim',
        'Hopper',
        'jim.hopper641@yahoo.com',
        '539 Muncie, Indiana 47303',
        '+1-750-980-1790'
    ),
    (
        'Gaara',
        'Sabaku',
        'gaara.sabaku434@outlook.com',
        '699 Konohagakure, Land of Fire',
        '+1-241-225-4531'
    ),
    (
        'Graha',
        'Tia',
        'graha.tia912@gmail.com',
        '599 Old Sharlayan, Northern Ilsabard, Eorzea',
        '+1-118-591-2421'
    ),
    (
        'Anduin',
        'Wrynn',
        'anduin.wrynn692@yahoo.com',
        '156 Orgrimmar, Durotar, Kalimdor',
        '+1-632-835-5490'
    ),
    (
        'Yshtola',
        'Rhul',
        'yshtola.rhul963@gmail.com',
        '367 The Waking Sands, Western Thanalan, Eorzea',
        '+1-898-876-3132'
    ),
    (
        'Tataru',
        'Taru',
        'tataru.taru790@outlook.com',
        '522 Limsa Lominsa, La Noscea, Eorzea',
        '+1-969-149-9708'
    ),
    (
        'Tyrande',
        'Whisperwind',
        'tyrande.whisperwind185@gmail.com',
        '780 Dalaran, Crystalsong Forest, Northrend',
        '+1-702-323-6249'
    ),
    (
        'Monkey',
        'Luffy',
        'monkey.luffy103@protonmail.com',
        '275 Dressrosa, New World',
        '+1-848-332-7635'
    ),
    (
        'Portgas',
        'Ace',
        'portgas.ace916@hotmail.com',
        '677 Alabasta Kingdom, Paradise',
        '+1-260-393-3085'
    ),
    (
        'Sasuke',
        'Uchiha',
        'sasuke.uchiha936@outlook.com',
        '716 Tanzaku Town, Land of Fire',
        '+1-541-230-9193'
    ),
    (
        'Jaina',
        'Proudmoore',
        'jaina.proudmoore788@outlook.com',
        '136 Boralus, Tiragarde Sound, Kul Tiras',
        '+1-339-965-2903'
    ),
    (
        'Steve',
        'Harrington',
        'steve.harrington333@protonmail.com',
        '327 Fishers, Indiana 46038',
        '+1-181-114-8624'
    ),
    (
        'Urianger',
        'Augurelt',
        'urianger.augurelt675@outlook.com',
        '96 The Waking Sands, Western Thanalan, Eorzea',
        '+1-997-133-3816'
    ),
    (
        'Lucas',
        'Sinclair',
        'lucas.sinclair395@outlook.com',
        '691 Hawkins, Indiana 47202',
        '+1-832-948-1133'
    ),
    (
        'Thrall',
        'Doomhammer',
        'thrall.doomhammer959@outlook.com',
        '318 Ironforge, Dun Morogh, Eastern Kingdoms',
        '+1-272-522-8425'
    ),
    (
        'Joyce',
        'Byers',
        'joyce.byers660@outlook.com',
        '829 Indianapolis, Indiana 46204',
        '+1-580-156-8286'
    ),
    (
        'Alisaie',
        'Leveilleur',
        'alisaie.leveilleur944@outlook.com',
        '751 Crystarium, The First, Norvrandt',
        '+1-811-428-5738'
    ),
    (
        'Malfurion',
        'Stormrage',
        'malfurion.stormrage455@outlook.com',
        '398 Darnassus, Teldrassil, Kalimdor',
        '+1-567-441-2010'
    ),
    (
        'Naruto',
        'Uzumaki',
        'naruto.uzumaki283@gmail.com',
        '932 Takigakure, Land of Waterfalls',
        '+1-212-681-1289'
    ),
    (
        'Lyse',
        'Hext',
        'lyse.hext361@hotmail.com',
        '430 Mor Dhona, Central Eorzea',
        '+1-570-282-9807'
    ),
    (
        'Eleven',
        'Hopper',
        'eleven.hopper555@hotmail.com',
        '499 Hawkins, Indiana 47203',
        '+1-631-541-9880'
    ),
    (
        'Nami',
        'Navigator',
        'nami.navigator130@hotmail.com',
        '785 Sabaody Archipelago, Paradise',
        '+1-158-576-9755'
    ),
    (
        'Illidan',
        'Stormrage',
        'illidan.stormrage590@hotmail.com',
        '306 Dalaran, Crystalsong Forest, Northrend',
        '+1-142-630-3710'
    ),
    (
        'Nancy',
        'Wheeler',
        'nancy.wheeler765@gmail.com',
        '236 Carmel, Indiana 46032',
        '+1-605-700-6946'
    ),
    (
        'Jiraiya',
        'Sannin',
        'jiraiya.sannin387@gmail.com',
        '216 Kusagakure, Land of Grass',
        '+1-129-678-8177'
    ),
    (
        'Grom',
        'Hellscream',
        'grom.hellscream233@hotmail.com',
        '930 Darnassus, Teldrassil, Kalimdor',
        '+1-397-235-4877'
    ),
    (
        'Itachi',
        'Uchiha',
        'itachi.uchiha677@gmail.com',
        '347 Amegakure, Land of Rain',
        '+1-960-794-9355'
    ),
    (
        'Sylvanas',
        'Windrunner',
        'sylvanas.windrunner448@hotmail.com',
        '203 Stormwind City, Elwynn Forest, Eastern Kingdoms',
        '+1-454-433-5771'
    ),
    (
        'Jonathan',
        'Byers',
        'jonathan.byers423@protonmail.com',
        '566 Carmel, Indiana 46032',
        '+1-518-541-8895'
    ),
    (
        'Mike',
        'Wheeler',
        'mike.wheeler129@protonmail.com',
        '605 Hawkins, Indiana 47203',
        '+1-269-383-2230'
    ),
    (
        'Tsunade',
        'Senju',
        'tsunade.senju238@hotmail.com',
        '23 Takigakure, Land of Waterfalls',
        '+1-912-822-2314'
    ),
    (
        'Roronoa',
        'Zoro',
        'roronoa.zoro595@yahoo.com',
        '118 Sabaody Archipelago, Paradise',
        '+1-621-829-9328'
    ),
    (
        'Jinbe',
        'Fishman',
        'jinbe.fishman429@hotmail.com',
        '156 Alabasta Kingdom, Paradise',
        '+1-271-851-6312'
    ),
    (
        'Minfilia',
        'Warde',
        'minfilia.warde293@yahoo.com',
        '321 The Waking Sands, Western Thanalan, Eorzea',
        '+1-486-149-6164'
    ),
    (
        'Sakura',
        'Haruno',
        'sakura.haruno979@hotmail.com',
        '190 Sunagakure, Land of Wind',
        '+1-521-236-9730'
    ),
    (
        'Tony',
        'Chopper',
        'tony.chopper859@hotmail.com',
        '859 Foosha Village, East Blue',
        '+1-614-515-8125'
    ),
    (
        'Estinien',
        'Wyrmblood',
        'estinien.wyrmblood548@hotmail.com',
        '346 Kugane, Hingashi, Far East',
        '+1-676-478-6331'
    ),
    (
        'Nico',
        'Robin',
        'nico.robin193@gmail.com',
        '876 Wano Country, New World',
        '+1-957-707-6768'
    ),
    (
        'Varian',
        'Wrynn',
        'varian.wrynn704@gmail.com',
        '756 Dalaran, Crystalsong Forest, Northrend',
        '+1-250-818-7353'
    ),
    (
        'Alphinaud',
        'Leveilleur',
        'alphinaud.leveilleur948@gmail.com',
        '456 The Waking Sands, Western Thanalan, Eorzea',
        '+1-429-959-3584'
    ),
    (
        'Vinsmoke',
        'Sanji',
        'vinsmoke.sanji605@gmail.com',
        '273 Fish-Man Island, Underwater',
        '+1-688-562-6151'
    ),
    (
        'Brook',
        'Soul',
        'brook.soul647@hotmail.com',
        '23 Alabasta Kingdom, Paradise',
        '+1-281-311-8487'
    ),
    (
        'Shikamaru',
        'Nara',
        'shikamaru.nara839@yahoo.com',
        '133 Amegakure, Land of Rain',
        '+1-524-938-5749'
    ),
    (
        'Thancred',
        'Waters',
        'thancred.waters573@hotmail.com',
        '811 Gridania, Black Shroud, Eorzea',
        '+1-928-101-6267'
    ),
    (
        'Hinata',
        'Hyuga',
        'hinata.hyuga809@outlook.com',
        '232 Kirigakure, Land of Water',
        '+1-714-226-5931'
    ),
    (
        'Dustin',
        'Henderson',
        'dustin.henderson833@outlook.com',
        '281 South Bend, Indiana 46601',
        '+1-548-754-4647'
    ),
    (
        'Arthas',
        'Menethil',
        'arthas.menethil867@hotmail.com',
        '805 Dazar''alor, Zuldazar, Zandalar',
        '+1-198-638-1067'
    );
;


INSERT INTO
    `training` (
        `id`,
        `name`,
        `description`,
        `onsite`,
        `duration_days`,
        `price`
    )
VALUES (
        1,
        'Professional Limsa Lominsa Workshop',
        'Professional Limsa Lominsa Workshop

Sharpen your skills as an adventurer-for-hire in Limsa Lominsa''s bustling marketplaces and dockside establishments, where seasoned merchants and guild masters teach you the fundamentals of maritime trade, seafaring logistics, and the cutthroat art of negotiation. Work alongside fellow treasure hunters and traders on live deals, from managing cargo shipments to brokering rare artifact exchanges, all under the guidance of Limsa''s most accomplished commerce veterans. You''ll graduate ready to navigate the Free City''s competitive economy and command respect from the Maelstrom''s most influential captains and entrepreneurs.',
        1,
        2,
        1286.8
    ),
    (
        2,
        'Introduction to Grand Line Workshop',
        '**Introduction to Grand Line Workshop**

Set sail and master the essential skills every aspiring pirate needs to navigate the world''s most legendary ocean route! Through hands-on navigation drills, real-world sea combat scenarios, and authentic case studies from the greatest naval voyages, you''ll learn to read the currents, strategize against rival crews, and chart your course toward becoming a true Grand Line legend. Upon completion, you''ll earn your official Grand Line Navigator''s Certification—your ticket to the adventure of a lifetime.',
        1,
        10,
        5557.22
    ),
    (
        3,
        'Expert Level Jutsu Training',
        '**Expert Level Jutsu Training**

Step into the dojo and push your chakra to its limits as elite senseis guide you through the most advanced jutsu techniques ever assembled. You''ll execute complex hand seals, master forbidden techniques, and learn to weaponize your chakra in ways that will leave even the strongest shinobi in awe. Graduate from this elite program as a battle-ready ninja capable of taking on any mission thrown your way.',
        1,
        3,
        3213.95
    ),
    (
        4,
        'Expert Level Mount Workshop',
        'Expert Level Mount Workshop

Strap in for an elite expedition where you''ll tackle the most challenging Mount techniques used by seasoned professionals in the field. You''ll push your limits through hands-on scenarios, master advanced strategies, and return as a certified expert ready to lead others. Leave as someone who doesn''t just understand Mount—you command it.',
        1,
        2,
        1690.45
    ),
    (
        5,
        'Advanced Bounty Bootcamp',
        'Course Name: Advanced Bounty Bootcamp

Description: Master the art of tracking down the most dangerous criminals across the Grand Line in this elite training program led by legendary bounty hunters. Sharpen your surveillance skills, learn target acquisition strategies, and collaborate with fellow hunters on high-stakes capture missions in our interactive simulations. Graduate ready to take on legendary bounties and build your reputation as the greatest hunter on the seas.',
        1,
        1,
        1467.59
    ),
    (
        6,
        'Complete Guide to Hawkins Training',
        'Course Name: Complete Guide to Hawkins Training

Description: Navigate the mysterious dimensions and supernatural phenomena of Hawkins, Indiana through hands-on labs that teach you to identify interdimensional rifts, operate cutting-edge detection equipment, and respond to anomalous events. Master real-world case studies from Hawkins'' most documented incidents while working alongside fellow operatives in practical scenarios. Earn your official Hawkins Operations Certification and become an expert in protecting your community from the unknown.',
        1,
        10,
        13636.69
    ),
    (
        7,
        'Professional White Mage Bootcamp',
        'Course name: Professional White Mage Bootcamp

New description: Join the most intensive healing regimen in Eorzea as you master the sacred art of White Magic—from casting Cure on panicked adventurers to coordinating party-wide shields during catastrophic boss encounters. Through hands-on dungeon runs, emergency triage simulations, and real combat scenarios, you''ll transform from a fumbling novice into a confident healer that raid groups actually want to invite. By graduation, you''ll have the skills to keep your entire party alive, even when they''re determined to stand in every AoE.',
        0,
        1,
        1035.36
    ),
    (
        8,
        'Mastering Grand Line Course',
        '**Mastering Grand Line Course**

Set sail on the world''s most dangerous and thrilling maritime adventure as you learn navigation secrets, survival tactics, and combat techniques from legendary captains who''ve conquered the Grand Line. Through intense hands-on training expeditions, treasure hunts, and crew-based challenges, you''ll develop the skills needed to traverse treacherous waters, outwit formidable enemies, and claim your place among the ocean''s elite adventurers. Train alongside fellow aspiring pirates and sailors to master the fundamentals that separate the legendary from the lost at sea.',
        1,
        1,
        1114.44
    ),
    (
        9,
        'Intensive The Party Bootcamp',
        '**Intensive The Party Bootcamp**

Join the most elite group of young operatives as they navigate covert missions, survive impossible odds, and master the art of teamwork in a world where nothing is what it seems. Through hands-on field training, real-world crisis simulations, and strategic case reviews, you''ll develop the skills needed to protect those you care about and uncover the truth hidden in plain sight. Graduate with full certification and the hard-earned knowledge that only The Party possesses.',
        1,
        5,
        3324.49
    ),
    (
        10,
        'Fundamentals of The Party Course',
        'Course Name: Fundamentals of The Party Course

Description: Jump into the electrifying world of The Party and learn the essential skills that keep the dance floor alive and the energy soaring! You''ll master the rhythm-reading techniques, crowd dynamics, and party coordination strategies that separate legendary events from forgettable ones. Graduate ready to orchestrate unforgettable celebrations and keep any gathering pulsing with pure excitement.',
        1,
        3,
        2241.41
    ),
    (
        11,
        'Strategic The Party Training',
        'Strategic The Party Training

Master the tactics and teamwork that keep your crew alive in a world of supernatural threats. Through immersive simulations, real-world case studies of successful group survival operations, and hands-on leadership labs, you''ll learn how to identify threats, coordinate your team under pressure, and navigate the Upside Down''s most dangerous challenges. Earn your official Strategic Party Certification and join the ranks of elite group commanders.',
        1,
        1,
        1362.21
    ),
    (
        12,
        'Strategic Alliance Bootcamp',
        'Strategic Alliance Bootcamp: Master the art of coordinating guild warfare, managing faction politics, and executing devastating combined operations across Azeroth. Led by veteran Alliance commanders who''ve conquered Mythic raids and dominated battlegrounds, you''ll develop real-time decision-making skills while organizing your own squad through live combat scenarios. Whether you''re a fresh recruit or a seasoned officer, this intensive program transforms you into a force multiplier capable of rallying hundreds under your banner.',
        1,
        2,
        2639.48
    ),
    (
        13,
        'Expert Level Rasengan Workshop',
        'Expert Level Rasengan Workshop: Master the legendary spinning sphere technique as you join elite ninja practitioners in the Hidden Leaf Village for intensive hands-on training. Through live demonstrations, combat drills against varied opponents, and chakra refinement exercises, you''ll perfect the fundamental strike, learn advanced variations, and develop the precision needed to execute this devastating jutsu in real combat situations. By the end of this workshop, you''ll have the control and power to deploy the Rasengan with lethal accuracy against any threat.',
        1,
        1,
        1000.81
    ),
    (
        14,
        'Professional Hokage Workshop',
        '**Professional Hokage Workshop**

Step into the hidden villages and experience the rigorous training that forges legendary leaders. Master the ancient philosophies of village governance, strategic combat deployment, and the delicate balance of diplomacy that keeps the ninja world in equilibrium. By graduation, you''ll be equipped to lead your own village through peace and conflict, making the critical decisions that shape ninja history.',
        1,
        2,
        1003
    ),
    (
        15,
        'Strategic Yonko Workshop',
        'Strategic Yonko Workshop

Join the ranks of the world''s most fearsome pirates as you master the art of commanding fleets, establishing vast territories, and building empires that span the Grand Line. Through intense tactical simulations and real-world crew management challenges, you''ll learn from legendary strategists how to outmaneuver rival Yonko and navigate the treacherous waters of pirate politics. Collaborate with fellow aspiring emperors in live scenarios where only cunning, charisma, and strategic brilliance will secure your dominance over the seas.',
        0,
        1,
        1212.88
    ),
    (
        16,
        'Practical Crystal Course',
        'Course Name: Practical Crystal Course

Description: Master the art of crystal manipulation and aetheric channeling under the guidance of seasoned practitioners as you hone your ability to sense, attune, and harness the raw power of crystals in all their forms. Through immersive practice sessions and collaborative expeditions, you''ll refine your fundamentals while tackling advanced techniques that only the most dedicated adventurers can master. Work alongside fellow students to solve real-world challenges of crystal resonance, aetheric balance, and elemental harmonization that will prepare you for any situation across Eorzea.',
        0,
        5,
        5190.32
    ),
    (
        17,
        'Expert Level Black Mage Training',
        '**Expert Level Black Mage Training**

Descend into the abyss of forbidden magic and emerge as a master of destruction, devastation, and the darkest arcane arts. This intensive practicum pushes you through real combat scenarios, advanced spell formations, and the manipulation of shadow energy itself—until channeling catastrophic power becomes second nature. By graduation, you''ll command the battlefield with the confidence of a true Black Mage, ready to obliterate any threat that dares cross your path.',
        1,
        3,
        2776.92
    ),
    (
        18,
        'Advanced Mythic Training',
        'Advanced Mythic Training

Master the ancient art of Mythic dungeons and conquer the most treacherous trials across Azeroth. This elite training program combines hands-on combat simulations, real-world raid strategies, and field-tested tactics from seasoned Mythic veterans to prepare you for impossible odds. Complete the gauntlet and earn your official Certification of Mythic Mastery—proof that you''ve achieved the legendary status required to lead the most dangerous expeditions.',
        0,
        10,
        11476.96
    ),
    (
        19,
        'Expert Level Hokage Workshop',
        '**Expert Level Hokage Workshop**

Master the ancient art of village leadership and elite ninja command at the highest echelon of the Hidden Leaf Village''s strategic academy. Through intensive combat simulations, forbidden jutsu studies, and mentorship from legendary Hokage advisors, you''ll develop the tactical brilliance, chakra manipulation, and diplomatic prowess required to lead a shinobi nation through its greatest challenges. Train alongside elite kunoichi and shinobi in our state-of-the-art dojo, where peer collaboration and real-world village scenarios will forge you into a Kage-level leader.',
        1,
        1,
        1465.67
    ),
    (
        20,
        'Mastering Mind Flayer Training',
        'Mastering Mind Flayer Training

Enroll in our elite neural infiltration program and discover the secrets of hive-mind dominance, telepathic control, and dimension-bending consciousness expansion. Through immersive live sessions, you''ll practice advanced spore deployment techniques, master the art of perfect assimilation, and collaborate with fellow operatives to coordinate hive objectives. Prepare yourself for the ultimate transformation—by course completion, you''ll command psychic networks and lead your own writhing collective.',
        1,
        1,
        882.48
    ),
    (
        21,
        'Expert Level Hunter Certification',
        'Expert Level Hunter Certification

Master the art of precision tracking, beast taming, and tactical combat in this immersive professional development program designed by the Hunters'' Guild itself. You''ll complete intensive practical labs where you''ll hone your marksmanship, study legendary hunts through detailed case studies, and prove your mastery through real-world scenarios before earning your official certification badge. Upon graduation, you''ll join the elite ranks of expert hunters qualified to take on the most dangerous contracts across Azeroth.',
        1,
        1,
        956.12
    ),
    (
        22,
        'Practical Russian Base Training',
        'Course Name: Practical Russian Base Training

Description: Welcome to the Hawkins Lab''s most exclusive intelligence program—master the fundamentals and advanced techniques of Russian Base operations under the guidance of our top field specialists. Through hands-on simulations, tactical projects, and collaborative deep-dives with fellow operatives, you''ll learn to navigate classified protocols, decode encrypted communications, and execute strategic initiatives that shape world events. Prepare yourself for what lies ahead in the shadow world.',
        0,
        10,
        5015.55
    ),
    (
        23,
        'Practical Treasure Training',
        'Course Name: Practical Treasure Training

Description: Join the Grand Line''s most comprehensive treasure hunting academy where you''ll master the art of locating, appraising, and securing legendary bounties through hands-on expeditions and real-world case studies of history''s greatest hauls. Navigate treacherous waters, decode ancient maps, and avoid rival crews in our immersive labs designed to prepare you for the unpredictable life of professional treasure recovery. Upon graduation, earn your official Treasure Hunter Certification and set sail with the skills to claim your own fortune.
',
        0,
        10,
        12678.44
    ),
    (
        24,
        'Advanced Dungeon Workshop',
        'Course name: Advanced Dungeon Workshop

Description: Master the art of navigating treacherous dungeons and conquering their deadliest encounters as you train alongside veteran dungeon masters. Through hands-on expeditions into actual dungeon labyrinths, you''ll develop essential survival tactics, learn to coordinate with party members in high-pressure situations, and discover how to exploit enemy weaknesses while managing resources and crowd control. By the time you complete this intensive workshop, you''ll be equipped to lead raids, adapt to any dungeon architecture, and emerge victorious against even the most formidable bosses.',
        1,
        3,
        3440.06
    ),
    (
        25,
        'Expert Level Bounty Bootcamp',
        '**Expert Level Bounty Bootcamp**

Welcome to the most intense training program in the Grand Line! Master the art of tracking, capturing, and apprehending the most notorious criminals across the seas under the guidance of legendary bounty hunters. Through live hunts, high-stakes negotiations, and collaborative crew missions, you''ll develop the tactical expertise and survival instincts needed to claim the biggest bounties and earn your place among the elite hunters.',
        1,
        1,
        760.66
    ),
    (
        26,
        'Intensive Luffy Course',
        '**Intensive Luffy Course**

Become the King of the Seas! Learn from legendary navigators and seasoned adventurers as you master the fundamentals of Luffy methodology—from rubber-powered problem solving to unwavering crew loyalty. You''ll develop your own unique Devil Fruit approach, conquer seemingly impossible challenges with sheer determination and creativity, and graduate with the skills to build a loyal crew that''ll follow you to the ends of the earth.',
        1,
        3,
        4289.45
    ),
    (
        27,
        'Complete Guide to Crystal Certification',
        'Course Name: Complete Guide to Crystal Certification

Description: Master the fundamental principles of crystal manipulation and attunement as you advance through the rigorous trials of the Arcanist''s Guild. Navigate perilous dungeons, harness aether flows, and learn to channel raw elemental power through hands-on combat scenarios where your crystal resonance will determine victory or defeat. Earn your official Crystal Certification and join the ranks of elite adventurers trusted to wield the most potent magical artifacts across the realm.',
        0,
        3,
        2157.09
    ),
    (
        28,
        'Mastering Akatsuki Training',
        '**Mastering Akatsuki Training**

Join our elite program to master the forbidden jutsu, combat strategies, and organizational operations of the Akatsuki. Through intensive hands-on training, you''ll develop deadly techniques, collaborate with fellow shinobi, and learn the secrets that have made the Akatsuki one of the most feared organizations in the ninja world. By course completion, you''ll be equipped with the advanced skills and tactical knowledge to operate at the highest levels of covert missions and chakra manipulation.',
        0,
        2,
        1389.68
    ),
    (
        29,
        'Strategic Shadow Clone Workshop',
        'Strategic Shadow Clone Workshop

Master the art of dividing your chakra and presence across the battlefield! In this hands-on course, you''ll learn advanced techniques for creating stable shadow clones, coordinating simultaneous attacks with your duplicates, and optimizing your chakra distribution for maximum strategic advantage. Through intensive practical training and tactical scenario exercises, you''ll gain the battlefield awareness and control needed to outmaneuver opponents through sheer numbers and coordination.',
        0,
        2,
        2811.43
    ),
    (
        30,
        'Introduction to Crystal Course',
        '**Introduction to Crystal Course**

Dive into the luminous world of crystal manipulation as you learn to harness their raw energy and unlock their hidden frequencies. Through hands-on practice and expert guidance, you''ll discover how to select, charge, and work with crystals to amplify your intentions and transform your environment. By the end of this immersive program, you''ll be a confident crystal practitioner ready to guide others through their own crystalline journeys.',
        1,
        2,
        2667.64
    ),
    (
        31,
        'Practical Psychic Powers Course',
        '**Practical Psychic Powers Course**

Unlock your latent telekinetic and telepathic abilities through hands-on experimentation in our state-of-the-art labs, where you''ll master real-world applications of psychic phenomena through carefully documented case studies and controlled exercises. Navigate the complexities of mind-bending powers, learn to control dangerous ability surges, and graduate with a professional certification that''s recognized by paranormal research institutions worldwide. This intensive program will transform you from novice to skilled psychic operative, prepared for the challenges of wielding extraordinary powers in the modern world.',
        1,
        10,
        11338.13
    ),
    (
        32,
        'Mastering Hidden Leaf Course',
        '**Mastering Hidden Leaf Course**

Step through the gates of the Hidden Leaf Village and undergo rigorous ninja training designed to hone your combat abilities, tactical thinking, and chakra control. From mastering foundational jutsu to executing advanced combat strategies, you''ll work through challenging scenarios that mirror real missions assigned by the Hokage himself. Graduate as a certified ninja with the practical skills and battlefield experience needed to protect the village and complete any mission with excellence.',
        1,
        3,
        2204.04
    ),
    (
        33,
        'Expert Level Straw Hat Certification',
        'Course Name: Expert Level Straw Hat Certification

Description: Join Captain Luffy''s inner circle as you master the legendary techniques and strategic philosophy behind the Straw Hat Pirates'' rise from underdogs to the world''s most formidable crew. Through intense practical training, real-world raid simulations, and analysis of your crew''s greatest victories against impossible odds, you''ll earn the credentials that prove you''re ready to navigate the Grand Line''s deadliest challenges. Upon completion, you''ll receive official certification recognizing you as an expert in Straw Hat operations—a credential respected from the East Blue to the furthest reaches of the New World.',
        0,
        5,
        4895.52
    ),
    (
        34,
        'Practical Dungeon Certification',
        '**Practical Dungeon Certification**

Delve into the depths and emerge as a certified Dungeon professional! This intensive hands-on program trains you in trap detection, monster negotiation, treasure protocols, and structural integrity assessment across all dungeon types—from ancient crypts to industrial-grade facilities. You''ll complete real-world certifications on actual dungeons, solve escape scenarios under time pressure, and leave with the credentials every major adventuring guild requires.',
        1,
        3,
        2057.39
    ),
    (
        35,
        'Introduction to Endwalker Course',
        'Course Name: Introduction to Endwalker

Description: Join us at the Scions of the Seventh Dawn headquarters to master the fundamental principles of Endwalker magic and combat, with guidance from battle-hardened adventurers who''ve walked the Final Steps themselves. Through immersive training sessions, you''ll sharpen your abilities alongside fellow Eorzean heroes, tackling real-world scenarios from the depths of Old Sharlayan to the edge of the universe. Prepare yourself for the challenges that await beyond the Endsinger''s reach.',
        1,
        1,
        1222.67
    ),
    (
        36,
        'Complete Guide to The Party Bootcamp',
        'Course Name: Complete Guide to The Party Bootcamp

Description: Join the elite ranks of The Party as you master the critical skills needed to survive supernatural threats, coordinate covert missions, and keep your crew alive against impossible odds. Through intensive hands-on training, real-world tactical scenarios, and mentorship from veteran Party members, you''ll learn everything from artifact identification and gate detection to group communication protocols and emergency evacuation procedures. Successfully complete the bootcamp and earn your official Party certification—your credential for joining the most important fight of our generation.',
        0,
        5,
        5117.55
    ),
    (
        37,
        'Introduction to Black Mage Certification',
        '**Introduction to Black Mage Certification**

Master the destructive arts as you harness the raw power of fire, ice, and lightning spells under the tutelage of seasoned mages. This intensive certification program covers advanced spell rotations, mana management, and tactical positioning on the battlefield—all essential skills for becoming a formidable Black Mage. Whether you''re a novice just discovering your first spell or a veteran looking to refine your magical arsenal, this hands-on training will prepare you to devastate enemies across Eorzea.',
        1,
        5,
        6721.75
    ),
    (
        38,
        'Complete Guide to Guild Workshop',
        'Course name: Complete Guide to Guild Workshop

Rewritten description: Master the secrets of running a thriving guild by learning from seasoned guild leaders and master craftspeople who''ve built legendary workshops across Azeroth. From recruiting and retaining talented members to managing resources, coordinating raids, and establishing your guild''s reputation, you''ll tackle real challenges that every guildmaster faces daily. Roll up your sleeves and complete hands-on assignments—manage a live workshop, mediate guild disputes, and oversee coordinated efforts that will test every leadership skill you''ve got.',
        0,
        5,
        6325.4
    ),
    (
        39,
        'Complete Guide to Bounty Workshop',
        'Course Name: Complete Guide to Bounty Workshop

Description: Join the most notorious bounty hunters across the Grand Line as they teach you the art of tracking, capturing, and negotiating rewards for the most dangerous criminals in the world. Through intense field training, interrogation techniques, and hands-on combat scenarios with real wanted criminals, you''ll master everything needed to climb the ranks from rookie hunter to legendary captain with a sky-high bounty on your own head. Test your skills in live hunts, learn to read wanted posters like a true pro, and discover how to survive encounters with Devil Fruit users and powerful swordsmen.',
        1,
        10,
        13414.39
    ),
    (
        40,
        'Practical Hidden Leaf Certification',
        'Course name: Practical Hidden Leaf Certification

Description: Master the elite techniques and tactical methodologies used by Hidden Leaf Village''s most skilled shinobi through rigorous hands-on training with veteran instructors who have proven their expertise in the field. Whether you''re just beginning your ninja journey or refining advanced combat skills, this certification will equip you with the essential knowledge to excel as a Hidden Leaf operative. Graduate ready to take on real-world missions and earn your place among the village''s most respected warriors.',
        0,
        1,
        1228.46
    ),
    (
        41,
        'Strategic PvP Training',
        'Strategic PvP Training

Dominate the arena by learning elite combat tactics, resource management, and psychological warfare from veteran fighters. Through intense sparring sessions and real-time scenario challenges, you''ll develop the instincts and strategies needed to outmaneuver any opponent. Walk out battle-tested and ready to claim victory.',
        0,
        5,
        7162.35
    ),
    (
        42,
        'Expert Level Conqueror''s Haki Workshop',
        'Course: Expert Level Conqueror''s Haki Workshop

Description: Master the overwhelming dominance of Conqueror''s Haki as you learn to project your willpower across battlefields and subjugate the weak-willed with your presence alone. Through grueling practical trials and high-stakes combat simulations, you''ll develop the ability to coat your attacks with this elite form of Haki, sense the strength of those around you, and trigger mass incapacitation in crowds of enemies. By the final day, you''ll face off against veteran warriors who will test every ounce of control and power you''ve cultivated.',
        1,
        3,
        2391.29
    ),
    (
        43,
        'Intensive White Mage Course',
        'Intensive White Mage Course

Master the sacred arts of healing and support magic in this rigorous professional development program for aspiring White Mages. Through intensive practical labs, real-world case studies involving life-or-death medical scenarios, and hands-on certification trials, you''ll develop the magical expertise and discipline required to keep your allies alive in any situation. Graduates earn their official White Mage certification and join the ranks of Eorzea''s most respected healers.',
        1,
        1,
        954.43
    ),
    (
        44,
        'Practical Hellfire Club Bootcamp',
        'Practical Hellfire Club Bootcamp

Join Hawkins'' most elite gaming collective for an intensive three-day immersion into the world of Dungeons & Dragons, strategic gameplay, and the art of recruiting fellow adventurers into your inner circle. Master the techniques of campaign design, character manipulation, and dominance through hands-on gaming sessions, real-world case studies from veteran players, and competitive scenarios that test your ability to command the table. Complete the bootcamp and earn your official Hellfire Club certification—proof that you''ve got what it takes to run the most feared gaming dynasty in Indiana.',
        0,
        10,
        13502.85
    ),
    (
        45,
        'Fundamentals of Mount Course',
        'Course Name: Fundamentals of Mount Course

Description: Master the ancient art of Mount taming and riding as you learn directly from experienced guides who''ve traversed the most treacherous terrains across the realm. Through hands-on expeditions, real-world scenarios involving stubborn griffons and temperamental wolves, and rigorous field trials, you''ll earn your official Rider''s Certification—a prestigious credential recognized across all kingdoms. By course completion, you''ll be equipped to handle any beast, navigate any landscape, and command respect wherever your hooves—or wings—may take you.',
        1,
        1,
        765.56
    ),
    (
        46,
        'Advanced The Party Certification',
        'Course Name: Advanced The Party Certification

Course Description: Master the art of survival, strategy, and supernatural combat as you train alongside Hawkins'' most elite defenders of humanity. Through intensive hands-on sessions, you''ll sharpen your tactical skills, learn to communicate under pressure, and discover how to stand united against otherworldly threats. By course completion, you''ll be a certified member of The Party, ready to protect your town and beyond from any darkness that dares to emerge from the Upside Down.',
        1,
        3,
        3120.24
    ),
    (
        47,
        'Practical Rasengan Course',
        'Course Name: Practical Rasengan Course

Description: Master the art of the Rasengan in this intensive hands-on training program designed to transform you into a skilled practitioner. Through rigorous practical labs, you''ll learn to gather, control, and weaponize chakra into a spiraling sphere of devastating power, with real-world case studies examining legendary techniques from master shinobi. Upon successful completion, you''ll earn your official Rasengan Practitioner Certification, proving your readiness to deploy this technique in the field.',
        1,
        5,
        2716.75
    ),
    (
        48,
        'Complete Guide to Genjutsu Bootcamp',
        'Complete Guide to Genjutsu Bootcamp: Step into the Hidden Leaf Village''s most demanding illusory arts academy, where you''ll break free from perception traps, master the chakra control required to weave reality-bending jutsu, and learn to see through even the most sinister mind games. By the end of this intensive program, you''ll possess the advanced techniques to deceive enemies, protect allies from false visions, and execute genjutsu that would make even seasoned shinobi question their reality. Graduate ready to deploy these dangerous illusions in the field with the precision and confidence of a true genjutsu specialist.',
        1,
        10,
        5792.8
    ),
    (
        49,
        'Mastering Ul''dah Bootcamp',
        '**Course name:** Mastering Ul''dah Bootcamp

**Description:** Enter the bustling streets of Ul''dah and master the essential survival skills of Eorzea''s premier trading hub through hands-on trials and real-world scenarios. Navigate the intricate systems of commerce, politics, and adventure that define this desert city, from negotiating with merchants to understanding the complex social hierarchies that shape life in the Sultanate. By the end of this bootcamp, you''ll move through Ul''dah with the confidence of a seasoned adventurer ready to make your mark in one of the realm''s most influential cities.',
        1,
        5,
        6036.37
    ),
    (
        50,
        'Intensive Shadowlands Course',
        '**Intensive Shadowlands Course**

Descend into the Shadowlands and master the dark arts of survival, necromancy, and soul manipulation through hands-on laboratories where you''ll harness anima, study the mechanics of the four covenants, and analyze real-world scenarios from the Eternal Conflict. Complete your training by passing the rigorous certification exam and claim your place among Shadowlands'' elite practitioners of power and shadow magic.',
        1,
        3,
        3842.51
    );


INSERT INTO
    `cart` (`id`, `user_id`, `created_at`)
VALUES (1, 4, '2025-12-23 23:30:38'),
    (2, 5, '2025-12-09 15:08:24'),
    (3, 13, '2025-12-28 01:12:53'),
    (4, 17, '2025-12-16 17:10:43');


INSERT INTO
    `cart_item` (
        `id`,
        `cart_id`,
        `training_id`,
        `quantity`,
        `unit_price`
    )
VALUES (1, 1, 14, 3, 1003),
    (2, 1, 45, 2, 765.56),
    (3, 1, 3, 3, 3213.95),
    (4, 2, 10, 1, 2241.41),
    (5, 2, 22, 2, 5015.55),
    (6, 3, 33, 3, 4895.52),
    (7, 3, 34, 2, 2057.39),
    (8, 3, 15, 1, 1212.88),
    (9, 4, 32, 3, 2204.04),
    (10, 4, 14, 3, 1003),
    (11, 4, 2, 3, 5557.22);


INSERT INTO
    `order` (
        `id`,
        `user_id`,
        `client_id`,
        `created_at`,
        `status`,
        `total`
    )
VALUES (
        1,
        18,
        42,
        '2025-01-07 20:18:30',
        'DRAFT',
        1912.24
    ),
    (
        2,
        20,
        34,
        '2025-01-07 22:27:29',
        'CANCELLED',
        22676.26
    ),
    (
        3,
        17,
        10,
        '2025-01-11 03:05:04',
        'CONFIRMED',
        4500.75
    ),
    (
        4,
        1,
        4,
        '2025-01-11 19:02:31',
        'CONFIRMED',
        26073.59
    ),
    (
        5,
        2,
        10,
        '2025-01-13 05:49:03',
        'CONFIRMED',
        14136.12
    ),
    (
        6,
        7,
        41,
        '2025-01-14 23:40:39',
        'CONFIRMED',
        2935.18
    ),
    (
        7,
        18,
        4,
        '2025-01-15 07:29:07',
        'CONFIRMED',
        3120.24
    ),
    (
        8,
        3,
        39,
        '2025-01-17 17:49:01',
        'DRAFT',
        41932.83
    ),
    (
        9,
        11,
        15,
        '2025-01-19 14:21:01',
        'CONFIRMED',
        22074.11
    ),
    (
        10,
        8,
        43,
        '2025-01-20 07:44:18',
        'CONFIRMED',
        13443.5
    ),
    (
        11,
        17,
        23,
        '2025-01-21 00:44:45',
        'DRAFT',
        6287.85
    ),
    (
        12,
        14,
        25,
        '2025-01-23 03:59:29',
        'CONFIRMED',
        16564.85
    ),
    (
        13,
        6,
        9,
        '2025-01-25 17:41:15',
        'CONFIRMED',
        37926.91
    ),
    (
        14,
        12,
        2,
        '2025-01-26 16:53:19',
        'CONFIRMED',
        20190.9
    ),
    (
        15,
        2,
        31,
        '2025-01-26 19:07:33',
        'CANCELLED',
        27108.22
    ),
    (
        16,
        15,
        40,
        '2025-01-30 05:10:48',
        'CONFIRMED',
        9973.47
    ),
    (
        17,
        3,
        19,
        '2025-01-29 22:35:57',
        'CONFIRMED',
        6427.9
    ),
    (
        18,
        11,
        12,
        '2025-01-31 19:53:27',
        'CONFIRMED',
        12477.1
    ),
    (
        19,
        8,
        5,
        '2025-02-01 17:01:12',
        'CONFIRMED',
        2070.72
    ),
    (
        20,
        12,
        40,
        '2025-02-03 06:45:26',
        'CONFIRMED',
        35387
    ),
    (
        21,
        4,
        34,
        '2025-02-05 13:47:22',
        'CONFIRMED',
        11627.67
    ),
    (
        22,
        3,
        15,
        '2025-02-07 06:23:49',
        'CONFIRMED',
        4400.85
    ),
    (
        23,
        2,
        6,
        '2025-02-08 04:31:30',
        'CONFIRMED',
        3780.17
    ),
    (
        24,
        2,
        40,
        '2025-02-09 06:54:47',
        'CONFIRMED',
        9791.04
    ),
    (
        25,
        4,
        9,
        '2025-02-10 21:58:29',
        'CONFIRMED',
        5606.47
    ),
    (
        26,
        5,
        35,
        '2025-02-12 16:41:54',
        'CONFIRMED',
        17030.8
    ),
    (
        27,
        19,
        40,
        '2025-02-13 10:20:18',
        'CANCELLED',
        17378.4
    ),
    (
        28,
        14,
        12,
        '2025-02-16 04:45:05',
        'CONFIRMED',
        2322.16
    ),
    (
        29,
        13,
        9,
        '2025-02-17 08:55:41',
        'CONFIRMED',
        5792.8
    ),
    (
        30,
        10,
        15,
        '2025-02-19 02:46:08',
        'CONFIRMED',
        44713.32
    ),
    (
        31,
        13,
        41,
        '2025-02-20 21:27:13',
        'CONFIRMED',
        2204.04
    ),
    (
        32,
        9,
        7,
        '2025-02-22 08:43:34',
        'CONFIRMED',
        16134.02
    ),
    (
        33,
        14,
        47,
        '2025-02-22 05:15:38',
        'CANCELLED',
        6172.17
    ),
    (
        34,
        6,
        41,
        '2025-02-24 06:50:53',
        'CONFIRMED',
        2070.72
    ),
    (
        35,
        20,
        26,
        '2025-02-25 20:17:09',
        'CONFIRMED',
        7790.81
    ),
    (
        36,
        19,
        36,
        '2025-02-27 14:07:16',
        'CONFIRMED',
        9286.59
    ),
    (
        37,
        5,
        1,
        '2025-03-01 17:13:23',
        'CONFIRMED',
        7286.61
    ),
    (
        38,
        5,
        24,
        '2025-03-02 22:29:46',
        'CONFIRMED',
        1035.36
    ),
    (
        39,
        14,
        10,
        '2025-03-03 03:46:58',
        'CONFIRMED',
        3120.24
    ),
    (
        40,
        15,
        38,
        '2025-03-04 22:02:36',
        'CONFIRMED',
        7757.11
    ),
    (
        41,
        5,
        18,
        '2025-03-06 10:44:40',
        'CONFIRMED',
        36471.32
    ),
    (
        42,
        12,
        27,
        '2025-03-07 06:54:56',
        'DRAFT',
        13682.17
    ),
    (
        43,
        12,
        6,
        '2025-03-10 07:08:13',
        'CANCELLED',
        6240.48
    ),
    (
        44,
        16,
        32,
        '2025-03-12 01:05:30',
        'DRAFT',
        18218.4
    ),
    (
        45,
        4,
        8,
        '2025-03-11 20:31:26',
        'CONFIRMED',
        31420.87
    ),
    (
        46,
        19,
        45,
        '2025-03-15 00:32:48',
        'CONFIRMED',
        11655.25
    ),
    (
        47,
        19,
        36,
        '2025-03-16 07:56:16',
        'CONFIRMED',
        11338.13
    ),
    (
        48,
        12,
        25,
        '2025-03-16 23:50:02',
        'CONFIRMED',
        44589.49
    ),
    (
        49,
        20,
        9,
        '2025-03-18 02:54:32',
        'CONFIRMED',
        20313.15
    ),
    (
        50,
        11,
        22,
        '2025-03-19 21:18:56',
        'DRAFT',
        36029.23
    ),
    (
        51,
        10,
        34,
        '2025-03-20 23:42:09',
        'DRAFT',
        9044.87
    ),
    (
        52,
        19,
        40,
        '2025-03-22 09:45:00',
        'CONFIRMED',
        34430.88
    ),
    (
        53,
        11,
        3,
        '2025-03-23 21:29:37',
        'CONFIRMED',
        17424.65
    ),
    (
        54,
        14,
        24,
        '2025-03-25 11:00:59',
        'CONFIRMED',
        13843.54
    ),
    (
        55,
        14,
        11,
        '2025-03-26 13:36:55',
        'CONFIRMED',
        1764.96
    ),
    (
        56,
        14,
        37,
        '2025-03-29 02:34:52',
        'CONFIRMED',
        10975.43
    ),
    (
        57,
        3,
        3,
        '2025-03-29 08:00:21',
        'CONFIRMED',
        21487.05
    ),
    (
        58,
        13,
        38,
        '2025-04-01 01:18:11',
        'CONFIRMED',
        19681.94
    ),
    (
        59,
        9,
        45,
        '2025-04-01 11:27:35',
        'CONFIRMED',
        5284.05
    ),
    (
        60,
        20,
        6,
        '2025-04-02 23:22:39',
        'CONFIRMED',
        15841.95
    ),
    (
        61,
        10,
        22,
        '2025-04-03 23:37:35',
        'CONFIRMED',
        1228.46
    ),
    (
        62,
        3,
        12,
        '2025-04-06 18:33:39',
        'CONFIRMED',
        5433.5
    ),
    (
        63,
        19,
        46,
        '2025-04-07 11:25:51',
        'CONFIRMED',
        17582.77
    ),
    (
        64,
        16,
        17,
        '2025-04-10 04:39:04',
        'CONFIRMED',
        6124.49
    ),
    (
        65,
        13,
        6,
        '2025-04-11 10:17:14',
        'CONFIRMED',
        7874.34
    ),
    (
        66,
        1,
        48,
        '2025-04-11 19:06:08',
        'CONFIRMED',
        8488.56
    ),
    (
        67,
        8,
        1,
        '2025-04-12 21:10:00',
        'CANCELLED',
        1003
    ),
    (
        68,
        16,
        16,
        '2025-04-16 01:20:55',
        'CANCELLED',
        34430.88
    ),
    (
        69,
        18,
        8,
        '2025-04-16 10:36:29',
        'CONFIRMED',
        1908.86
    ),
    (
        70,
        4,
        10,
        '2025-04-17 07:24:56',
        'CONFIRMED',
        44506.55
    ),
    (
        71,
        8,
        35,
        '2025-04-18 23:02:05',
        'DRAFT',
        11476.96
    ),
    (
        72,
        11,
        26,
        '2025-04-20 01:16:08',
        'CONFIRMED',
        14764.3
    ),
    (
        73,
        8,
        29,
        '2025-04-22 19:42:42',
        'CONFIRMED',
        66779.47
    ),
    (
        74,
        14,
        39,
        '2025-04-24 03:57:49',
        'DRAFT',
        4314.18
    ),
    (
        75,
        11,
        20,
        '2025-04-24 09:09:39',
        'CONFIRMED',
        31142.16
    ),
    (
        76,
        13,
        19,
        '2025-04-27 12:56:02',
        'CONFIRMED',
        6185.79
    ),
    (
        77,
        9,
        4,
        '2025-04-27 13:34:57',
        'CONFIRMED',
        13782.08
    ),
    (
        78,
        20,
        19,
        '2025-04-30 01:52:34',
        'DRAFT',
        11303.32
    ),
    (
        79,
        20,
        41,
        '2025-05-02 04:45:51',
        'CONFIRMED',
        9018.27
    ),
    (
        80,
        15,
        25,
        '2025-05-02 22:52:04',
        'CANCELLED',
        13648.14
    ),
    (
        81,
        6,
        32,
        '2025-05-04 18:58:00',
        'CONFIRMED',
        2241.41
    ),
    (
        82,
        3,
        21,
        '2025-05-05 15:34:44',
        'CONFIRMED',
        19455.65
    ),
    (
        83,
        2,
        8,
        '2025-05-06 17:34:46',
        'CONFIRMED',
        15570.96
    ),
    (
        84,
        9,
        4,
        '2025-05-08 06:36:19',
        'CONFIRMED',
        8913.48
    ),
    (
        85,
        1,
        46,
        '2025-05-10 19:05:33',
        'CONFIRMED',
        2157.09
    ),
    (
        86,
        9,
        34,
        '2025-05-11 09:02:08',
        'CONFIRMED',
        6648.98
    ),
    (
        87,
        8,
        28,
        '2025-05-12 05:10:55',
        'CANCELLED',
        20681.36
    ),
    (
        88,
        15,
        1,
        '2025-05-14 01:16:32',
        'DRAFT',
        25910.8
    ),
    (
        89,
        11,
        9,
        '2025-05-15 14:49:07',
        'DRAFT',
        6612.12
    ),
    (
        90,
        19,
        7,
        '2025-05-17 04:38:10',
        'CANCELLED',
        16520.47
    ),
    (
        91,
        18,
        29,
        '2025-05-19 11:10:25',
        'CONFIRMED',
        2241.41
    ),
    (
        92,
        12,
        45,
        '2025-05-19 07:03:43',
        'CONFIRMED',
        13502.85
    ),
    (
        93,
        2,
        22,
        '2025-05-21 12:51:07',
        'CONFIRMED',
        11114.44
    ),
    (
        94,
        6,
        26,
        '2025-05-22 04:47:17',
        'CONFIRMED',
        1035.36
    ),
    (
        95,
        6,
        50,
        '2025-05-25 11:31:49',
        'CONFIRMED',
        16511.91
    ),
    (
        96,
        14,
        7,
        '2025-05-25 18:44:48',
        'CONFIRMED',
        12678.44
    ),
    (
        97,
        5,
        1,
        '2025-05-26 23:59:11',
        'CONFIRMED',
        6034.58
    ),
    (
        98,
        10,
        4,
        '2025-05-29 15:06:06',
        'CONFIRMED',
        7922.88
    ),
    (
        99,
        7,
        6,
        '2025-05-30 21:03:43',
        'CONFIRMED',
        1000.81
    ),
    (
        100,
        12,
        43,
        '2025-05-31 14:55:32',
        'CONFIRMED',
        11641.03
    ),
    (
        101,
        17,
        31,
        '2025-06-03 06:44:26',
        'CONFIRMED',
        11338.13
    ),
    (
        102,
        6,
        10,
        '2025-06-04 01:45:15',
        'CONFIRMED',
        1228.46
    ),
    (
        103,
        4,
        41,
        '2025-06-04 11:13:02',
        'CONFIRMED',
        25709.16
    ),
    (
        104,
        19,
        12,
        '2025-06-07 08:07:30',
        'DRAFT',
        9686.64
    ),
    (
        105,
        19,
        44,
        '2025-06-08 13:16:18',
        'CONFIRMED',
        9791.04
    ),
    (
        106,
        5,
        48,
        '2025-06-09 02:37:17',
        'CONFIRMED',
        6753.97
    ),
    (
        107,
        20,
        25,
        '2025-06-10 10:28:32',
        'CONFIRMED',
        760.66
    ),
    (
        108,
        2,
        21,
        '2025-06-11 22:50:21',
        'CONFIRMED',
        56254.82
    ),
    (
        109,
        2,
        40,
        '2025-06-13 09:54:45',
        'CANCELLED',
        48072.48
    ),
    (
        110,
        14,
        45,
        '2025-06-14 23:52:13',
        'CONFIRMED',
        5830.5
    ),
    (
        111,
        17,
        24,
        '2025-06-17 00:51:15',
        'CANCELLED',
        45981.9
    ),
    (
        112,
        4,
        20,
        '2025-06-17 22:44:47',
        'CONFIRMED',
        17784.65
    ),
    (
        113,
        8,
        25,
        '2025-06-20 04:50:50',
        'CANCELLED',
        10469.17
    ),
    (
        114,
        18,
        30,
        '2025-06-20 13:08:22',
        'CONFIRMED',
        14071.39
    ),
    (
        115,
        6,
        9,
        '2025-06-21 21:35:30',
        'CONFIRMED',
        15522.96
    ),
    (
        116,
        14,
        48,
        '2025-06-24 13:08:54',
        'CONFIRMED',
        11495.55
    ),
    (
        117,
        8,
        47,
        '2025-06-25 06:23:04',
        'CONFIRMED',
        10868.28
    ),
    (
        118,
        3,
        9,
        '2025-06-27 13:46:44',
        'CONFIRMED',
        13820.7
    ),
    (
        119,
        18,
        37,
        '2025-06-27 18:04:36',
        'CONFIRMED',
        3343.32
    ),
    (
        120,
        9,
        16,
        '2025-06-30 02:13:48',
        'CONFIRMED',
        11342.91
    ),
    (
        121,
        2,
        42,
        '2025-06-30 22:51:46',
        'CONFIRMED',
        12981.67
    ),
    (
        122,
        1,
        9,
        '2025-07-02 19:36:10',
        'CONFIRMED',
        49018.53
    ),
    (
        123,
        18,
        15,
        '2025-07-04 08:34:18',
        'CONFIRMED',
        4482.82
    ),
    (
        124,
        11,
        32,
        '2025-07-05 02:04:37',
        'CONFIRMED',
        15352.65
    ),
    (
        125,
        7,
        34,
        '2025-07-07 18:48:06',
        'CONFIRMED',
        30943.56
    ),
    (
        126,
        8,
        33,
        '2025-07-08 07:36:00',
        'CONFIRMED',
        14445.14
    ),
    (
        127,
        5,
        34,
        '2025-07-10 06:46:37',
        'DRAFT',
        21421.54
    ),
    (
        128,
        12,
        27,
        '2025-07-11 14:37:40',
        'CONFIRMED',
        3860.4
    ),
    (
        129,
        20,
        4,
        '2025-07-12 19:06:31',
        'CONFIRMED',
        15570.96
    ),
    (
        130,
        9,
        32,
        '2025-07-14 10:32:26',
        'CONFIRMED',
        13636.69
    ),
    (
        131,
        14,
        27,
        '2025-07-16 20:25:01',
        'CONFIRMED',
        4314.18
    ),
    (
        132,
        6,
        33,
        '2025-07-16 21:51:00',
        'CONFIRMED',
        5553.84
    ),
    (
        133,
        12,
        6,
        '2025-07-18 08:01:22',
        'CONFIRMED',
        4371.21
    ),
    (
        134,
        16,
        21,
        '2025-07-20 02:11:11',
        'CONFIRMED',
        52426.05
    ),
    (
        135,
        7,
        3,
        '2025-07-22 19:26:54',
        'CONFIRMED',
        71606.46
    ),
    (
        136,
        14,
        5,
        '2025-07-24 05:04:56',
        'CONFIRMED',
        1764.96
    ),
    (
        137,
        10,
        24,
        '2025-07-24 04:05:32',
        'CONFIRMED',
        22564
    ),
    (
        138,
        12,
        15,
        '2025-07-26 01:53:05',
        'CONFIRMED',
        27232.27
    ),
    (
        139,
        16,
        16,
        '2025-07-27 19:33:39',
        'CONFIRMED',
        40243.17
    ),
    (
        140,
        2,
        6,
        '2025-07-30 04:00:07',
        'CONFIRMED',
        27005.7
    ),
    (
        141,
        15,
        36,
        '2025-07-30 12:08:54',
        'CONFIRMED',
        4845.51
    ),
    (
        142,
        9,
        45,
        '2025-07-31 14:07:06',
        'CONFIRMED',
        40243.17
    ),
    (
        143,
        15,
        8,
        '2025-08-02 07:10:49',
        'CONFIRMED',
        1764.96
    ),
    (
        144,
        10,
        13,
        '2025-08-04 10:33:53',
        'CONFIRMED',
        2779.36
    ),
    (
        145,
        19,
        8,
        '2025-08-06 10:44:03',
        'CONFIRMED',
        4314.18
    ),
    (
        146,
        3,
        30,
        '2025-08-06 23:03:50',
        'DRAFT',
        32904.81
    ),
    (
        147,
        17,
        3,
        '2025-08-08 15:34:06',
        'CONFIRMED',
        12868.35
    ),
    (
        148,
        7,
        44,
        '2025-08-10 08:13:42',
        'CONFIRMED',
        1114.44
    ),
    (
        149,
        18,
        10,
        '2025-08-12 07:03:20',
        'CONFIRMED',
        21487.05
    ),
    (
        150,
        18,
        38,
        '2025-08-13 16:02:55',
        'CANCELLED',
        63374.53
    ),
    (
        151,
        17,
        28,
        '2025-08-14 21:40:01',
        'CONFIRMED',
        15493.85
    ),
    (
        152,
        7,
        32,
        '2025-08-15 08:15:45',
        'CONFIRMED',
        45286.19
    ),
    (
        153,
        5,
        19,
        '2025-08-16 11:10:44',
        'CONFIRMED',
        8229.56
    ),
    (
        154,
        3,
        47,
        '2025-08-18 21:58:28',
        'CONFIRMED',
        2863.29
    ),
    (
        155,
        9,
        48,
        '2025-08-20 03:00:19',
        'CONFIRMED',
        882.48
    ),
    (
        156,
        20,
        34,
        '2025-08-20 22:04:28',
        'CONFIRMED',
        12411
    ),
    (
        157,
        18,
        50,
        '2025-08-23 21:26:30',
        'CONFIRMED',
        5553.84
    ),
    (
        158,
        14,
        11,
        '2025-08-24 10:59:11',
        'CONFIRMED',
        34014.39
    ),
    (
        159,
        7,
        45,
        '2025-08-25 16:33:52',
        'CONFIRMED',
        954.43
    ),
    (
        160,
        19,
        15,
        '2025-08-26 16:57:03',
        'CONFIRMED',
        88382.93
    ),
    (
        161,
        3,
        7,
        '2025-08-29 18:40:41',
        'CONFIRMED',
        21291.83
    ),
    (
        162,
        18,
        22,
        '2025-08-30 22:59:07',
        'CANCELLED',
        1035.36
    ),
    (
        163,
        19,
        25,
        '2025-08-31 21:31:14',
        'CONFIRMED',
        7119.52
    ),
    (
        164,
        19,
        39,
        '2025-09-02 13:25:15',
        'CONFIRMED',
        60266.75
    ),
    (
        165,
        1,
        43,
        '2025-09-03 09:09:49',
        'CONFIRMED',
        6724.23
    ),
    (
        166,
        1,
        23,
        '2025-09-05 22:03:07',
        'CONFIRMED',
        3638.64
    ),
    (
        167,
        17,
        37,
        '2025-09-07 07:51:27',
        'CONFIRMED',
        3860.4
    ),
    (
        168,
        9,
        11,
        '2025-09-07 19:12:47',
        'CONFIRMED',
        15508.07
    ),
    (
        169,
        19,
        28,
        '2025-09-09 18:07:30',
        'CONFIRMED',
        4086.63
    ),
    (
        170,
        12,
        39,
        '2025-09-11 13:34:25',
        'CONFIRMED',
        3002.43
    ),
    (
        171,
        18,
        26,
        '2025-09-13 06:29:31',
        'CONFIRMED',
        18998.92
    ),
    (
        172,
        12,
        23,
        '2025-09-13 15:56:18',
        'CONFIRMED',
        6325.4
    ),
    (
        173,
        1,
        19,
        '2025-09-14 23:08:57',
        'DRAFT',
        5531.25
    ),
    (
        174,
        4,
        19,
        '2025-09-15 23:00:33',
        'CONFIRMED',
        7900.7
    ),
    (
        175,
        16,
        49,
        '2025-09-18 11:18:52',
        'CONFIRMED',
        42805.23
    ),
    (
        176,
        11,
        16,
        '2025-09-19 07:22:33',
        'CONFIRMED',
        9771.22
    ),
    (
        177,
        2,
        18,
        '2025-09-21 03:36:15',
        'CANCELLED',
        18056.87
    ),
    (
        178,
        10,
        35,
        '2025-09-21 22:33:25',
        'CANCELLED',
        48193.57
    ),
    (
        179,
        10,
        4,
        '2025-09-24 12:27:31',
        'CANCELLED',
        13502.85
    ),
    (
        180,
        13,
        31,
        '2025-09-26 12:09:05',
        'CANCELLED',
        2296.68
    ),
    (
        181,
        1,
        32,
        '2025-09-26 04:54:35',
        'CONFIRMED',
        43241.73
    ),
    (
        182,
        14,
        16,
        '2025-09-28 03:02:32',
        'CONFIRMED',
        19228.25
    ),
    (
        183,
        17,
        36,
        '2025-09-29 17:40:26',
        'CONFIRMED',
        8990.64
    ),
    (
        184,
        20,
        4,
        '2025-10-01 00:34:14',
        'CONFIRMED',
        5395.91
    ),
    (
        185,
        14,
        31,
        '2025-10-02 07:24:01',
        'CONFIRMED',
        11356.93
    ),
    (
        186,
        12,
        23,
        '2025-10-04 06:39:10',
        'CONFIRMED',
        18000.09
    ),
    (
        187,
        9,
        17,
        '2025-10-06 01:29:31',
        'CONFIRMED',
        15885.04
    ),
    (
        188,
        15,
        36,
        '2025-10-07 14:51:47',
        'CONFIRMED',
        9357.92
    ),
    (
        189,
        16,
        27,
        '2025-10-09 09:31:15',
        'CONFIRMED',
        17086.52
    ),
    (
        190,
        15,
        28,
        '2025-10-11 00:59:07',
        'CONFIRMED',
        3528.03
    ),
    (
        191,
        9,
        32,
        '2025-10-12 06:41:41',
        'CANCELLED',
        40273.11
    ),
    (
        192,
        5,
        10,
        '2025-10-12 05:02:08',
        'CONFIRMED',
        5649.87
    ),
    (
        193,
        7,
        34,
        '2025-10-14 10:12:03',
        'DRAFT',
        2006
    ),
    (
        194,
        17,
        46,
        '2025-10-16 20:01:51',
        'CONFIRMED',
        14230.6
    ),
    (
        195,
        11,
        22,
        '2025-10-17 20:47:35',
        'CONFIRMED',
        3380.9
    ),
    (
        196,
        4,
        5,
        '2025-10-19 00:20:32',
        'CONFIRMED',
        7463.26
    ),
    (
        197,
        6,
        38,
        '2025-10-21 02:47:24',
        'CONFIRMED',
        3380.9
    ),
    (
        198,
        7,
        26,
        '2025-10-22 19:04:22',
        'CONFIRMED',
        11123.16
    ),
    (
        199,
        13,
        41,
        '2025-10-22 14:52:47',
        'DRAFT',
        1912.24
    ),
    (
        200,
        4,
        11,
        '2025-10-25 17:46:54',
        'DRAFT',
        7674.88
    ),
    (
        201,
        4,
        12,
        '2025-10-26 04:39:59',
        'CONFIRMED',
        8150.25
    ),
    (
        202,
        18,
        31,
        '2025-10-28 08:38:27',
        'CONFIRMED',
        12842.53
    ),
    (
        203,
        1,
        4,
        '2025-10-29 07:54:43',
        'CONFIRMED',
        6612.12
    ),
    (
        204,
        13,
        39,
        '2025-10-31 15:15:08',
        'CONFIRMED',
        2204.04
    ),
    (
        205,
        1,
        27,
        '2025-11-01 14:32:12',
        'CONFIRMED',
        12468.08
    ),
    (
        206,
        1,
        2,
        '2025-11-02 01:37:59',
        'CONFIRMED',
        40334.94
    ),
    (
        207,
        19,
        28,
        '2025-11-03 18:15:15',
        'CONFIRMED',
        23399.29
    ),
    (
        208,
        18,
        26,
        '2025-11-05 17:57:11',
        'CONFIRMED',
        11527.53
    ),
    (
        209,
        16,
        8,
        '2025-11-06 05:12:04',
        'CANCELLED',
        7162.35
    ),
    (
        210,
        13,
        19,
        '2025-11-07 11:57:21',
        'CONFIRMED',
        11476.96
    ),
    (
        211,
        4,
        28,
        '2025-11-09 11:32:35',
        'CONFIRMED',
        3381.88
    ),
    (
        212,
        16,
        28,
        '2025-11-11 08:33:47',
        'CONFIRMED',
        24657.27
    ),
    (
        213,
        10,
        47,
        '2025-11-12 15:00:22',
        'CONFIRMED',
        3009
    ),
    (
        214,
        12,
        23,
        '2025-11-14 17:57:21',
        'CANCELLED',
        2425.76
    ),
    (
        215,
        17,
        11,
        '2025-11-16 00:03:42',
        'CONFIRMED',
        2863.29
    ),
    (
        216,
        11,
        39,
        '2025-11-17 14:04:52',
        'CONFIRMED',
        28138.48
    ),
    (
        217,
        6,
        46,
        '2025-11-19 04:55:18',
        'CONFIRMED',
        40910.07
    ),
    (
        218,
        12,
        4,
        '2025-11-19 11:40:38',
        'CONFIRMED',
        4408.08
    ),
    (
        219,
        10,
        36,
        '2025-11-20 23:17:16',
        'DRAFT',
        41604.75
    ),
    (
        220,
        13,
        1,
        '2025-11-23 11:49:51',
        'CONFIRMED',
        6648.98
    ),
    (
        221,
        14,
        44,
        '2025-11-24 23:23:41',
        'CONFIRMED',
        15358.72
    ),
    (
        222,
        2,
        4,
        '2025-11-26 22:27:47',
        'CONFIRMED',
        6240.48
    ),
    (
        223,
        2,
        43,
        '2025-11-26 14:36:38',
        'CONFIRMED',
        8434.29
    ),
    (
        224,
        18,
        35,
        '2025-11-28 17:50:03',
        'CONFIRMED',
        35766.45
    ),
    (
        225,
        6,
        36,
        '2025-11-30 13:06:30',
        'DRAFT',
        4670.22
    ),
    (
        226,
        7,
        27,
        '2025-12-01 09:14:19',
        'CANCELLED',
        3009
    ),
    (
        227,
        15,
        11,
        '2025-12-03 22:06:22',
        'CONFIRMED',
        4443.06
    ),
    (
        228,
        9,
        44,
        '2025-12-04 20:39:14',
        'DRAFT',
        31033.16
    ),
    (
        229,
        11,
        39,
        '2025-12-05 16:35:07',
        'CONFIRMED',
        28546.62
    ),
    (
        230,
        12,
        34,
        '2025-12-08 15:11:33',
        'CONFIRMED',
        9204.18
    ),
    (
        231,
        20,
        16,
        '2025-12-10 01:09:02',
        'CONFIRMED',
        4920.68
    ),
    (
        232,
        1,
        4,
        '2025-12-10 17:26:52',
        'DRAFT',
        9813.35
    ),
    (
        233,
        6,
        13,
        '2025-12-11 01:34:11',
        'DRAFT',
        5397.82
    ),
    (
        234,
        7,
        21,
        '2025-12-13 05:46:24',
        'CONFIRMED',
        7918.44
    ),
    (
        235,
        16,
        32,
        '2025-12-14 18:44:59',
        'CONFIRMED',
        13024.1
    ),
    (
        236,
        4,
        11,
        '2025-12-16 13:25:47',
        'CONFIRMED',
        34430.88
    ),
    (
        237,
        13,
        15,
        '2025-12-17 23:51:23',
        'CONFIRMED',
        2391.29
    ),
    (
        238,
        14,
        2,
        '2025-12-19 19:06:25',
        'CONFIRMED',
        6839.97
    ),
    (
        239,
        16,
        48,
        '2025-12-21 03:57:14',
        'CONFIRMED',
        16435.12
    ),
    (
        240,
        12,
        7,
        '2025-12-22 19:36:40',
        'CANCELLED',
        41622.99
    ),
    (
        241,
        13,
        45,
        '2025-12-22 23:37:46',
        'CANCELLED',
        18976.2
    ),
    (
        242,
        9,
        7,
        '2025-12-25 13:54:47',
        'CONFIRMED',
        1362.21
    ),
    (
        243,
        7,
        36,
        '2025-12-27 07:36:50',
        'CONFIRMED',
        6185.79
    ),
    (
        244,
        10,
        27,
        '2025-12-27 04:09:47',
        'CONFIRMED',
        3264.24
    ),
    (
        245,
        8,
        31,
        '2025-12-29 07:38:20',
        'DRAFT',
        19057.63
    ),
    (
        246,
        1,
        42,
        '2025-12-31 21:30:39',
        'CONFIRMED',
        5962.65
    ),
    (
        247,
        15,
        17,
        '2026-01-02 04:22:11',
        'CONFIRMED',
        38827.89
    ),
    (
        248,
        10,
        29,
        '2026-01-02 22:10:33',
        'CONFIRMED',
        8437.95
    ),
    (
        249,
        1,
        8,
        '2026-01-04 14:40:34',
        'CONFIRMED',
        7187.82
    ),
    (
        250,
        18,
        29,
        '2026-01-05 22:53:13',
        'CONFIRMED',
        21271.35
    );


INSERT INTO
    `order_line` (
        `id`,
        `order_id`,
        `training_id`,
        `quantity`,
        `unit_price`
    )
VALUES (1, 1, 21, 2, 956.12),
    (2, 2, 31, 2, 11338.13),
    (3, 3, 1, 1, 1286.8),
    (4, 3, 3, 1, 3213.95),
    (5, 4, 42, 3, 2391.29),
    (6, 4, 25, 2, 760.66),
    (7, 4, 48, 3, 5792.8),
    (8, 5, 2, 1, 5557.22),
    (9, 5, 26, 2, 4289.45),
    (10, 6, 5, 2, 1467.59),
    (11, 7, 46, 1, 3120.24),
    (12, 8, 31, 3, 11338.13),
    (13, 8, 12, 3, 2639.48),
    (14, 9, 43, 2, 954.43),
    (15, 9, 37, 3, 6721.75),
    (16, 10, 37, 2, 6721.75),
    (17, 11, 35, 2, 1222.67),
    (18, 11, 50, 1, 3842.51),
    (19, 12, 19, 3, 1465.67),
    (20, 12, 10, 2, 2241.41),
    (21, 12, 50, 2, 3842.51),
    (22, 13, 31, 2, 11338.13),
    (23, 13, 22, 1, 5015.55),
    (24, 13, 36, 2, 5117.55),
    (25, 14, 39, 1, 13414.39),
    (26, 14, 35, 1, 1222.67),
    (27, 14, 17, 2, 2776.92),
    (28, 15, 9, 1, 3324.49),
    (29, 15, 41, 3, 7162.35),
    (30, 15, 45, 3, 765.56),
    (31, 16, 9, 3, 3324.49),
    (32, 17, 3, 2, 3213.95),
    (33, 18, 50, 2, 3842.51),
    (34, 18, 5, 1, 1467.59),
    (35, 18, 9, 1, 3324.49),
    (36, 19, 7, 2, 1035.36),
    (37, 20, 18, 3, 11476.96),
    (38, 20, 21, 1, 956.12),
    (39, 21, 32, 3, 2204.04),
    (40, 21, 22, 1, 5015.55),
    (41, 22, 5, 2, 1467.59),
    (42, 22, 19, 1, 1465.67),
    (43, 23, 28, 2, 1389.68),
    (44, 23, 13, 1, 1000.81),
    (45, 24, 33, 2, 4895.52),
    (46, 25, 25, 3, 760.66),
    (47, 25, 9, 1, 3324.49),
    (48, 26, 18, 1, 11476.96),
    (49, 26, 17, 2, 2776.92),
    (50, 27, 48, 3, 5792.8),
    (51, 28, 1, 1, 1286.8),
    (52, 28, 7, 1, 1035.36),
    (53, 29, 48, 1, 5792.8),
    (54, 30, 2, 3, 5557.22),
    (55, 30, 39, 2, 13414.39),
    (56, 30, 15, 1, 1212.88),
    (57, 31, 32, 1, 2204.04),
    (58, 32, 40, 1, 1228.46),
    (59, 32, 27, 3, 2157.09),
    (60, 32, 29, 3, 2811.43),
    (61, 33, 34, 3, 2057.39),
    (62, 34, 7, 2, 1035.36),
    (63, 35, 11, 2, 1362.21),
    (64, 35, 34, 1, 2057.39),
    (65, 35, 14, 3, 1003),
    (66, 36, 36, 1, 5117.55),
    (67, 36, 28, 3, 1389.68),
    (68, 37, 34, 3, 2057.39),
    (69, 37, 8, 1, 1114.44),
    (70, 38, 7, 1, 1035.36),
    (71, 39, 46, 1, 3120.24),
    (72, 40, 37, 1, 6721.75),
    (73, 40, 7, 1, 1035.36),
    (74, 41, 2, 2, 5557.22),
    (75, 41, 23, 2, 12678.44),
    (76, 42, 36, 2, 5117.55),
    (77, 42, 34, 1, 2057.39),
    (78, 42, 28, 1, 1389.68),
    (79, 43, 46, 2, 3120.24),
    (80, 44, 20, 3, 882.48),
    (81, 44, 16, 3, 5190.32),
    (82, 45, 24, 2, 3440.06),
    (83, 45, 41, 1, 7162.35),
    (84, 45, 48, 3, 5792.8),
    (85, 46, 17, 3, 2776.92),
    (86, 46, 9, 1, 3324.49),
    (87, 47, 31, 1, 11338.13),
    (88, 48, 8, 3, 1114.44),
    (89, 48, 14, 1, 1003),
    (90, 48, 39, 3, 13414.39),
    (91, 49, 32, 1, 2204.04),
    (92, 49, 49, 3, 6036.37),
    (93, 50, 39, 1, 13414.39),
    (94, 50, 38, 3, 6325.4),
    (95, 50, 15, 3, 1212.88),
    (96, 51, 27, 3, 2157.09),
    (97, 51, 1, 2, 1286.8),
    (98, 52, 18, 3, 11476.96),
    (99, 53, 26, 3, 4289.45),
    (100, 53, 43, 2, 954.43),
    (101, 53, 20, 3, 882.48),
    (102, 54, 10, 2, 2241.41),
    (103, 54, 46, 3, 3120.24),
    (104, 55, 20, 2, 882.48),
    (105, 56, 21, 3, 956.12),
    (106, 56, 42, 2, 2391.29),
    (107, 56, 9, 1, 3324.49),
    (108, 57, 41, 3, 7162.35),
    (109, 58, 2, 2, 5557.22),
    (110, 58, 50, 2, 3842.51),
    (111, 58, 20, 1, 882.48),
    (112, 59, 25, 2, 760.66),
    (113, 59, 25, 2, 760.66),
    (114, 59, 10, 1, 2241.41),
    (115, 60, 22, 1, 5015.55),
    (116, 60, 33, 2, 4895.52),
    (117, 60, 7, 1, 1035.36),
    (118, 61, 40, 1, 1228.46),
    (119, 62, 47, 2, 2716.75),
    (120, 63, 3, 1, 3213.95),
    (121, 63, 39, 1, 13414.39),
    (122, 63, 43, 1, 954.43),
    (123, 64, 50, 1, 3842.51),
    (124, 64, 25, 1, 760.66),
    (125, 64, 25, 2, 760.66),
    (126, 65, 40, 3, 1228.46),
    (127, 65, 25, 2, 760.66),
    (128, 65, 30, 1, 2667.64),
    (129, 66, 2, 1, 5557.22),
    (130, 66, 19, 2, 1465.67),
    (131, 67, 14, 1, 1003),
    (132, 68, 18, 3, 11476.96),
    (133, 69, 43, 2, 954.43),
    (134, 70, 49, 3, 6036.37),
    (135, 70, 41, 2, 7162.35),
    (136, 70, 49, 2, 6036.37),
    (137, 71, 18, 1, 11476.96),
    (138, 72, 5, 3, 1467.59),
    (139, 72, 13, 1, 1000.81),
    (140, 72, 46, 3, 3120.24),
    (141, 73, 23, 3, 12678.44),
    (142, 73, 26, 2, 4289.45),
    (143, 73, 37, 3, 6721.75),
    (144, 74, 27, 2, 2157.09),
    (145, 75, 32, 3, 2204.04),
    (146, 75, 17, 2, 2776.92),
    (147, 75, 38, 3, 6325.4),
    (148, 76, 8, 1, 1114.44),
    (149, 76, 4, 3, 1690.45),
    (150, 77, 30, 1, 2667.64),
    (151, 77, 2, 2, 5557.22),
    (152, 78, 11, 2, 1362.21),
    (153, 78, 26, 2, 4289.45),
    (154, 79, 46, 1, 3120.24),
    (155, 79, 22, 1, 5015.55),
    (156, 79, 20, 1, 882.48),
    (157, 80, 21, 1, 956.12),
    (158, 80, 40, 2, 1228.46),
    (159, 80, 36, 2, 5117.55),
    (160, 81, 10, 1, 2241.41),
    (161, 82, 7, 3, 1035.36),
    (162, 82, 39, 1, 13414.39),
    (163, 82, 5, 2, 1467.59),
    (164, 83, 16, 3, 5190.32),
    (165, 84, 50, 2, 3842.51),
    (166, 84, 40, 1, 1228.46),
    (167, 85, 27, 1, 2157.09),
    (168, 86, 9, 2, 3324.49),
    (169, 87, 30, 3, 2667.64),
    (170, 87, 23, 1, 12678.44),
    (171, 88, 48, 1, 5792.8),
    (172, 88, 22, 3, 5015.55),
    (173, 88, 4, 3, 1690.45),
    (174, 89, 32, 3, 2204.04),
    (175, 90, 39, 1, 13414.39),
    (176, 90, 7, 3, 1035.36),
    (177, 91, 10, 1, 2241.41),
    (178, 92, 44, 1, 13502.85),
    (179, 93, 2, 2, 5557.22),
    (180, 94, 7, 1, 1035.36),
    (181, 95, 3, 1, 3213.95),
    (182, 95, 9, 2, 3324.49),
    (183, 95, 9, 2, 3324.49),
    (184, 96, 23, 1, 12678.44),
    (185, 97, 4, 1, 1690.45),
    (186, 97, 8, 3, 1114.44),
    (187, 97, 13, 1, 1000.81),
    (188, 98, 24, 1, 3440.06),
    (189, 98, 10, 2, 2241.41),
    (190, 99, 13, 1, 1000.81),
    (191, 100, 27, 3, 2157.09),
    (192, 100, 35, 2, 1222.67),
    (193, 100, 11, 2, 1362.21),
    (194, 101, 31, 1, 11338.13),
    (195, 102, 40, 1, 1228.46),
    (196, 103, 48, 3, 5792.8),
    (197, 103, 17, 3, 2776.92),
    (198, 104, 50, 2, 3842.51),
    (199, 104, 13, 2, 1000.81),
    (200, 105, 33, 2, 4895.52),
    (201, 106, 28, 2, 1389.68),
    (202, 106, 3, 1, 3213.95),
    (203, 106, 25, 1, 760.66),
    (204, 107, 25, 1, 760.66),
    (205, 108, 41, 2, 7162.35),
    (206, 108, 18, 2, 11476.96),
    (207, 108, 38, 3, 6325.4),
    (208, 109, 42, 3, 2391.29),
    (209, 109, 23, 3, 12678.44),
    (210, 109, 43, 3, 954.43),
    (211, 110, 7, 3, 1035.36),
    (212, 110, 11, 2, 1362.21),
    (213, 111, 44, 2, 13502.85),
    (214, 111, 38, 3, 6325.4),
    (215, 112, 16, 3, 5190.32),
    (216, 112, 15, 1, 1212.88),
    (217, 112, 13, 1, 1000.81),
    (218, 113, 13, 1, 1000.81),
    (219, 113, 19, 3, 1465.67),
    (220, 113, 4, 3, 1690.45),
    (221, 114, 34, 2, 2057.39),
    (222, 114, 5, 3, 1467.59),
    (223, 114, 17, 2, 2776.92),
    (224, 115, 33, 1, 4895.52),
    (225, 115, 17, 3, 2776.92),
    (226, 115, 45, 3, 765.56),
    (227, 116, 9, 2, 3324.49),
    (228, 116, 4, 2, 1690.45),
    (229, 116, 19, 1, 1465.67),
    (230, 117, 19, 3, 1465.67),
    (231, 117, 27, 3, 2157.09),
    (232, 118, 16, 2, 5190.32),
    (233, 118, 24, 1, 3440.06),
    (234, 119, 8, 2, 1114.44),
    (235, 119, 8, 1, 1114.44),
    (236, 120, 28, 3, 1389.68),
    (237, 120, 42, 3, 2391.29),
    (238, 121, 26, 2, 4289.45),
    (239, 121, 5, 3, 1467.59),
    (240, 122, 6, 2, 13636.69),
    (241, 122, 17, 3, 2776.92),
    (242, 122, 39, 1, 13414.39),
    (243, 123, 10, 2, 2241.41),
    (244, 124, 36, 3, 5117.55),
    (245, 125, 34, 2, 2057.39),
    (246, 125, 39, 2, 13414.39),
    (247, 126, 20, 3, 882.48),
    (248, 126, 46, 2, 3120.24),
    (249, 126, 2, 1, 5557.22),
    (250, 127, 38, 3, 6325.4),
    (251, 127, 35, 2, 1222.67),
    (252, 128, 1, 3, 1286.8),
    (253, 129, 16, 3, 5190.32),
    (254, 130, 6, 1, 13636.69),
    (255, 131, 27, 2, 2157.09),
    (256, 132, 17, 2, 2776.92),
    (257, 133, 14, 3, 1003),
    (258, 133, 11, 1, 1362.21),
    (259, 134, 47, 3, 2716.75),
    (260, 134, 23, 3, 12678.44),
    (261, 134, 46, 2, 3120.24),
    (262, 135, 6, 2, 13636.69),
    (263, 135, 23, 2, 12678.44),
    (264, 135, 38, 3, 6325.4),
    (265, 136, 20, 2, 882.48),
    (266, 137, 33, 3, 4895.52),
    (267, 137, 40, 1, 1228.46),
    (268, 137, 9, 2, 3324.49),
    (269, 138, 44, 1, 13502.85),
    (270, 138, 42, 1, 2391.29),
    (271, 138, 31, 1, 11338.13),
    (272, 139, 39, 3, 13414.39),
    (273, 140, 44, 2, 13502.85),
    (274, 141, 14, 1, 1003),
    (275, 141, 50, 1, 3842.51),
    (276, 142, 39, 3, 13414.39),
    (277, 143, 20, 2, 882.48),
    (278, 144, 28, 2, 1389.68),
    (279, 145, 27, 2, 2157.09),
    (280, 146, 31, 1, 11338.13),
    (281, 146, 24, 2, 3440.06),
    (282, 146, 33, 3, 4895.52),
    (283, 147, 26, 3, 4289.45),
    (284, 148, 8, 1, 1114.44),
    (285, 149, 41, 3, 7162.35),
    (286, 150, 2, 3, 5557.22),
    (287, 150, 6, 3, 13636.69),
    (288, 150, 48, 1, 5792.8),
    (289, 151, 3, 1, 3213.95),
    (290, 151, 36, 1, 5117.55),
    (291, 151, 41, 1, 7162.35),
    (292, 152, 1, 2, 1286.8),
    (293, 152, 44, 3, 13502.85),
    (294, 152, 32, 1, 2204.04),
    (295, 153, 34, 3, 2057.39),
    (296, 153, 34, 1, 2057.39),
    (297, 154, 43, 3, 954.43),
    (298, 155, 20, 1, 882.48),
    (299, 156, 30, 3, 2667.64),
    (300, 156, 32, 2, 2204.04),
    (301, 157, 17, 2, 2776.92),
    (302, 158, 31, 3, 11338.13),
    (303, 159, 43, 1, 954.43),
    (304, 160, 18, 3, 11476.96),
    (305, 160, 37, 2, 6721.75),
    (306, 160, 44, 3, 13502.85),
    (307, 161, 8, 2, 1114.44),
    (308, 161, 42, 1, 2391.29),
    (309, 161, 2, 3, 5557.22),
    (310, 162, 7, 1, 1035.36),
    (311, 163, 5, 3, 1467.59),
    (312, 163, 47, 1, 2716.75),
    (313, 164, 41, 2, 7162.35),
    (314, 164, 44, 3, 13502.85),
    (315, 164, 47, 2, 2716.75),
    (316, 165, 10, 3, 2241.41),
    (317, 166, 15, 3, 1212.88),
    (318, 167, 1, 3, 1286.8),
    (319, 168, 17, 2, 2776.92),
    (320, 168, 19, 3, 1465.67),
    (321, 168, 2, 1, 5557.22),
    (322, 169, 11, 3, 1362.21),
    (323, 170, 13, 3, 1000.81),
    (324, 171, 35, 2, 1222.67),
    (325, 171, 8, 2, 1114.44),
    (326, 171, 41, 2, 7162.35),
    (327, 172, 38, 1, 6325.4),
    (328, 173, 11, 1, 1362.21),
    (329, 173, 28, 3, 1389.68),
    (330, 174, 10, 1, 2241.41),
    (331, 174, 3, 1, 3213.95),
    (332, 174, 35, 2, 1222.67),
    (333, 175, 45, 3, 765.56),
    (334, 175, 44, 3, 13502.85),
    (335, 176, 8, 3, 1114.44),
    (336, 176, 3, 2, 3213.95),
    (337, 177, 27, 3, 2157.09),
    (338, 177, 48, 2, 5792.8),
    (339, 178, 50, 2, 3842.51),
    (340, 178, 44, 3, 13502.85),
    (341, 179, 44, 1, 13502.85),
    (342, 180, 45, 3, 765.56),
    (343, 181, 5, 3, 1467.59),
    (344, 181, 18, 3, 11476.96),
    (345, 181, 32, 2, 2204.04),
    (346, 182, 29, 1, 2811.43),
    (347, 182, 13, 3, 1000.81),
    (348, 182, 39, 1, 13414.39),
    (349, 183, 42, 1, 2391.29),
    (350, 183, 19, 2, 1465.67),
    (351, 183, 35, 3, 1222.67),
    (352, 184, 13, 2, 1000.81),
    (353, 184, 14, 1, 1003),
    (354, 184, 42, 1, 2391.29),
    (355, 185, 10, 1, 2241.41),
    (356, 185, 42, 1, 2391.29),
    (357, 185, 10, 3, 2241.41),
    (358, 186, 36, 3, 5117.55),
    (359, 186, 20, 3, 882.48),
    (360, 187, 32, 2, 2204.04),
    (361, 187, 18, 1, 11476.96),
    (362, 188, 11, 1, 1362.21),
    (363, 188, 12, 2, 2639.48),
    (364, 188, 47, 1, 2716.75),
    (365, 189, 32, 2, 2204.04),
    (366, 189, 23, 1, 12678.44),
    (367, 190, 1, 2, 1286.8),
    (368, 190, 43, 1, 954.43),
    (369, 191, 18, 1, 11476.96),
    (370, 191, 37, 2, 6721.75),
    (371, 191, 36, 3, 5117.55),
    (372, 192, 13, 3, 1000.81),
    (373, 192, 20, 3, 882.48),
    (374, 193, 14, 2, 1003),
    (375, 194, 37, 1, 6721.75),
    (376, 194, 5, 3, 1467.59),
    (377, 194, 7, 3, 1035.36),
    (378, 195, 4, 2, 1690.45),
    (379, 196, 3, 2, 3213.95),
    (380, 196, 7, 1, 1035.36),
    (381, 197, 4, 2, 1690.45),
    (382, 198, 46, 1, 3120.24),
    (383, 198, 30, 3, 2667.64),
    (384, 199, 21, 2, 956.12),
    (385, 200, 28, 2, 1389.68),
    (386, 200, 33, 1, 4895.52),
    (387, 201, 47, 3, 2716.75),
    (388, 202, 22, 2, 5015.55),
    (389, 202, 29, 1, 2811.43),
    (390, 203, 32, 3, 2204.04),
    (391, 204, 32, 1, 2204.04),
    (392, 205, 48, 2, 5792.8),
    (393, 205, 20, 1, 882.48),
    (394, 206, 39, 1, 13414.39),
    (395, 206, 41, 3, 7162.35),
    (396, 206, 47, 2, 2716.75),
    (397, 207, 41, 3, 7162.35),
    (398, 207, 21, 2, 956.12),
    (399, 208, 50, 3, 3842.51),
    (400, 209, 41, 1, 7162.35),
    (401, 210, 18, 1, 11476.96),
    (402, 211, 15, 2, 1212.88),
    (403, 211, 21, 1, 956.12),
    (404, 212, 15, 1, 1212.88),
    (405, 212, 30, 2, 2667.64),
    (406, 212, 49, 3, 6036.37),
    (407, 213, 14, 3, 1003),
    (408, 214, 15, 2, 1212.88),
    (409, 215, 43, 3, 954.43),
    (410, 216, 17, 3, 2776.92),
    (411, 216, 17, 3, 2776.92),
    (412, 216, 18, 1, 11476.96),
    (413, 217, 6, 3, 13636.69),
    (414, 218, 32, 2, 2204.04),
    (415, 219, 18, 3, 11476.96),
    (416, 219, 42, 3, 2391.29),
    (417, 220, 9, 2, 3324.49),
    (418, 221, 50, 3, 3842.51),
    (419, 221, 8, 1, 1114.44),
    (420, 221, 47, 1, 2716.75),
    (421, 222, 46, 2, 3120.24),
    (422, 223, 29, 3, 2811.43),
    (423, 224, 46, 2, 3120.24),
    (424, 224, 37, 3, 6721.75),
    (425, 224, 46, 3, 3120.24),
    (426, 225, 8, 2, 1114.44),
    (427, 225, 15, 1, 1212.88),
    (428, 225, 40, 1, 1228.46),
    (429, 226, 14, 3, 1003),
    (430, 227, 14, 1, 1003),
    (431, 227, 24, 1, 3440.06),
    (432, 228, 6, 2, 13636.69),
    (433, 228, 11, 2, 1362.21),
    (434, 228, 7, 1, 1035.36),
    (435, 229, 5, 2, 1467.59),
    (436, 229, 31, 2, 11338.13),
    (437, 229, 5, 2, 1467.59),
    (438, 230, 36, 1, 5117.55),
    (439, 230, 11, 3, 1362.21),
    (440, 231, 43, 3, 954.43),
    (441, 231, 34, 1, 2057.39),
    (442, 232, 42, 3, 2391.29),
    (443, 232, 12, 1, 2639.48),
    (444, 233, 13, 1, 1000.81),
    (445, 233, 19, 3, 1465.67),
    (446, 234, 12, 3, 2639.48),
    (447, 235, 37, 1, 6721.75),
    (448, 235, 1, 1, 1286.8),
    (449, 235, 22, 1, 5015.55),
    (450, 236, 18, 3, 11476.96),
    (451, 237, 42, 1, 2391.29),
    (452, 238, 34, 1, 2057.39),
    (453, 238, 42, 2, 2391.29),
    (454, 239, 10, 1, 2241.41),
    (455, 239, 47, 1, 2716.75),
    (456, 239, 18, 1, 11476.96),
    (457, 240, 44, 3, 13502.85),
    (458, 240, 8, 1, 1114.44),
    (459, 241, 38, 3, 6325.4),
    (460, 242, 11, 1, 1362.21),
    (461, 243, 4, 3, 1690.45),
    (462, 243, 8, 1, 1114.44),
    (463, 244, 7, 1, 1035.36),
    (464, 244, 8, 2, 1114.44),
    (465, 245, 9, 3, 3324.49),
    (466, 245, 32, 1, 2204.04),
    (467, 245, 24, 2, 3440.06),
    (468, 246, 8, 1, 1114.44),
    (469, 246, 40, 2, 1228.46),
    (470, 246, 42, 1, 2391.29),
    (471, 247, 19, 3, 1465.67),
    (472, 247, 18, 3, 11476.96),
    (473, 248, 43, 3, 954.43),
    (474, 248, 12, 1, 2639.48),
    (475, 248, 5, 2, 1467.59),
    (476, 249, 12, 2, 2639.48),
    (477, 249, 43, 2, 954.43),
    (478, 250, 29, 3, 2811.43),
    (479, 250, 5, 3, 1467.59),
    (480, 250, 29, 3, 2811.43);


SET FOREIGN_KEY_CHECKS = 1;