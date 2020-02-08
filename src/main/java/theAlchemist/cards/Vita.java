package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import theAlchemist.AlchemistMod;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.powers.VitalityPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Vita extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Vita.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Vita.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
	private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
	private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;
	
	private static final int COST = 3;
	private static final int HEAL = 20;
	private static final int UPGRADE_PLUS_HEAL = 5; //upgrade
	private static final int VITALITY = 7;
	private static final int REGEN = 3;
	
	public static final int STAGE = 2;
	public static final AbstractElement[] CONSTITUENTS = {new Fulgur(), new Naturae()};
	public static final AbstractElement[] REACTANTS = null;
	public static final AbstractElement[] PRODUCTS  = null;
	
	public Vita()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Life", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseMagicNumber = HEAL;
		this.exhaust = true;
		
		this.tags.add(CardTags.HEALING);
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VitalityPower(player, VITALITY), VITALITY));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RegenPower(player, REGEN), REGEN));
		
		super.use(player, monster); // Don't change this part if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_HEAL);
			initializeDescription();
		}
	}
}
