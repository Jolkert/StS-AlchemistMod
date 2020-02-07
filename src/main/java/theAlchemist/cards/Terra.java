package theAlchemist.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAlchemist.AlchemistMod;
import theAlchemist.characters.TheAlchemist;
import theAlchemist.enums.AlchemistCardTags;
import theAlchemist.powers.ElementPower;

import static theAlchemist.AlchemistMod.makeCardPath;

public class Terra extends AbstractElement
{
	public static final String ID = AlchemistMod.makeID(Terra.class.getSimpleName());
	public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	
	public static final String IMAGE = makeCardPath("Terra.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
	private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
	private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
	private static final AbstractCard.CardColor COLOR = TheAlchemist.Enums.COLOR_PLATINUM;
	
	private static final int COST = 1;
	private static final int BLOCK = 6;
	private static final int UPGRADE_PLUS_BLOCK = 2; //upgrade
	
	public static final int STAGE = 0;
	public static final AbstractElement[] CONSTITUENTS = null;
	public static final AbstractElement[] REACTANTS = {new Ignis(), new Aqua()};
	public static final AbstractElement[] PRODUCTS  = {new Tremor(), new Naturae()};
	
	public Terra()
	{
		super(ID, NAME, IMAGE, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, "Earth", STAGE, CONSTITUENTS, REACTANTS, PRODUCTS);
		this.baseBlock = BLOCK;
		this.exhaust = true;
		
		this.tags.add(AlchemistCardTags.ELEMENT);
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster)
	{
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		super.use(player, monster); // Don't change these parts if you're copy-pasting this for a new element pls thx -Jolkert 2020-02-06
	}
	
	@Override
	public void upgrade()
	{
		if(!upgraded)
		{
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}
}
