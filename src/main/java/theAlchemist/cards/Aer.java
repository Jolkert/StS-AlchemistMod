package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.actions.AerAction;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.powers.ElementPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Aer extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Aer.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Aer.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	private static final int DAMAGE = 7;
	
	public static final int STAGE = 0;
	public static final AbstractElement[] CONSTITUENTS = null;
	public static final AbstractElement[] REACTANTS = {new Ignis(), new Aqua()};
	public static final AbstractElement[] PRODUCTS  = {new Fulgur(), new Glacies()};
	
	public Aer()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Air", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseDamage = DAMAGE;
		this.exhaust = true;
		
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		AbstractDungeon.actionManager.addToBottom(new AerAction());
		super.use(player, monster); // Don't change this part if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
	}
	
	@Override
	public void triggerOnGlowCheck() {
		if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() &&
				AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(AlchemistCardTags.ELEMENT))
			this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
		else
			this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
		
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}
}
